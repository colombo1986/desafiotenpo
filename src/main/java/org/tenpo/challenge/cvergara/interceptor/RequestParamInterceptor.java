package org.tenpo.challenge.cvergara.interceptor;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.tenpo.challenge.cvergara.model.entity.History;
import org.tenpo.challenge.cvergara.repository.HistoryRepository;
import java.nio.charset.StandardCharsets;


@Component
public class RequestParamInterceptor  implements WebFilter {

    HistoryRepository historyRepository;

    public RequestParamInterceptor(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(RequestParamInterceptor.class);

    //funcionando
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//
//        // Obtiene los parámetros de la URL (Query Params)
//        var requestedUri = exchange.getRequest().getURI().toString();
//        var queryParams = exchange.getRequest().getQueryParams().toSingleValueMap().toString();
//
//        log.info("Interceptando petición a URL: {} con parámetros: {}", requestedUri, queryParams);
//        History history = new History(requestedUri, queryParams);
//        return historyRepository.save(history)
//                .doOnSuccess(savedHistory -> log.info("Guardado History con id: {}", savedHistory.getId()))
//                .doOnError(error -> log.error("Error al guardar History: {}", error.getMessage()))
//                .then(chain.filter(exchange));
//    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        // Capturar información de la solicitud
        String requestedUri = exchange.getRequest().getURI().toString();
        String queryParams = exchange.getRequest().getQueryParams().toSingleValueMap().toString();

        log.info("Interceptando petición a URL: {} con parámetros: {}", requestedUri, queryParams);

        // Crear una instancia de History con los datos de la solicitud
        History history = new History(requestedUri, queryParams);

        // Decorar la respuesta para capturar el ResponseStatus y el ResponseBody
        ServerHttpResponse originalResponse = exchange.getResponse();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

            private final StringBuilder responseBodyBuilder = new StringBuilder();

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

                    return super.writeWith(fluxBody
                            .doOnNext(dataBuffer -> {
                                // Leer el contenido del DataBuffer
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);

                                // Convertir a String y almacenar en el StringBuilder
                                String responseBody = new String(content, StandardCharsets.UTF_8);
                                responseBodyBuilder.append(responseBody);
                            })
                            .doFinally(signalType -> {
                                // Establecer el ResponseBody y ResponseStatus en la entidad History
                                history.setResponseBody(responseBodyBuilder.toString());
                                history.setResponseStatus(getStatusCode() != null ? getStatusCode().value() : null);
                            }));
                }

                // Fallback si el body no es un Flux
                return super.writeWith(body);
            }
        };

        // Crear un intercambio decorado con la respuesta decorada
        ServerWebExchange decoratedExchange = exchange.mutate().response(decoratedResponse).build();

        // Procesar la solicitud y luego guardar la entidad History en la base de datos
        return chain.filter(decoratedExchange)
                .then(Mono.defer(() -> {
                    log.info("Guardando en la base de datos: {}", history);
                    return historyRepository.save(history)
                            .doOnSuccess(saved -> log.info("Guardado History con id: {}", saved.getId()))
                            .doOnError(error -> log.error("Error al guardar History: {}", error.getMessage()));
                })).then();
    }
}