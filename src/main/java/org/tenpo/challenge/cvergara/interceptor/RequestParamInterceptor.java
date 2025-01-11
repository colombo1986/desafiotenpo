package org.tenpo.challenge.cvergara.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
public class RequestParamInterceptor  implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestParamInterceptor.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        // Obtiene los parámetros de la URL (Query Params)
        var requestedUri = exchange.getRequest().getURI();
        var queryParams = exchange.getRequest().getQueryParams().toSingleValueMap().toString();


        log.info("Interceptando petición a URL: {} con parámetros: {}", requestedUri, queryParams);


        return chain.filter(exchange);
    }
}