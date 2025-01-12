package org.tenpo.challenge.cvergara.httpclient;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.tenpo.challenge.cvergara.repository.RedisRepository;
import reactor.core.publisher.Mono;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RandomNumberClient {

    private static final Logger log = LoggerFactory.getLogger(RandomNumberClient.class);

    private final WebClient webClient;
    private final RedisRepository redisRepository;

    public RandomNumberClient(WebClient.Builder webClientBuilder, RedisRepository redisRepository) {
        this.webClient = webClientBuilder
                .baseUrl("http://www.randomnumberapi.com/api/v1.0")
                .build();
        this.redisRepository = redisRepository;
    }

    /**
     * Llama al endpoint para obtener un número aleatorio entre 1 y 99.
     * La API retorna un array JSON (por ejemplo: [42]), así que parseamos
     * la respuesta a un List<Integer> y luego tomamos el primer (y único) elemento.
     *
     * Si falla la llamada a randomnumberapi:
     *   1) Se intenta obtener el número guardado en Redis (si existe).
     *   2) Si Redis no tiene registro, se lanza un error HTTP 503 .
     */
    public Mono<Integer> getRandomNumber() {
        return webClient
                .get()
                .uri("/random?min=1&max=99&count=1")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Integer>>() {})
                .map(List::getFirst) // Extrae el primer valor (ej.: 42)
                .doOnNext(num -> log.info("Número aleatorio recibido: {}", num))
                .flatMap(num ->
                        // Guardar en Redis y luego retornar el número
                        redisRepository.saveNumber(num)
                                .doOnNext(saved -> {
                                    if (saved) {
                                        log.info("Número {} guardado en Redis con TTL de 30 minutos", num);
                                    } else {
                                        log.warn("No se pudo guardar el número {} en Redis", num);
                                    }
                                })
                                .thenReturn(num) // Retornar el número original
                )
                // Manejar error si randomnumberapi falla:
                .onErrorResume(error -> {
                    log.error("Error llamando a randomnumberapi: {}", error.getMessage());

                    // Intentar recuperar de Redis:
                    return redisRepository.getLastSavedNumber()
                            .switchIfEmpty(Mono.error(new ResponseStatusException(
                                    HttpStatus.SERVICE_UNAVAILABLE,
                                    "La API externa falló y no hay un número guardado en Redis")))
                            .doOnNext(cachedNum -> log.info("Retornando número {} desde Redis", cachedNum));
                });
    }
}

