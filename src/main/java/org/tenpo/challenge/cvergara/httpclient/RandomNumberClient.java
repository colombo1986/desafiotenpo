package org.tenpo.challenge.cvergara.httpclient;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.tenpo.challenge.cvergara.repository.RedisRepository;
import reactor.core.publisher.Mono;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;

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
     */
    public Mono<Integer> getRandomNumber() {
        return webClient
                .get()
                .uri("/random?min=1&max=99&count=1")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Integer>>() {})
                .map(List::getFirst) // Extrae el primer valor (ej: 42)
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
                );
    }
}

