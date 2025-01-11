package org.tenpo.challenge.cvergara.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;

@Component
public class RandomNumberClient {

    private static final Logger log = LoggerFactory.getLogger(RandomNumberClient.class);

    private final WebClient webClient;

    public RandomNumberClient(WebClient.Builder webClientBuilder) {

        this.webClient = webClientBuilder
                .baseUrl("http://www.randomnumberapi.com/api/v1.0")
                .build();
    }

    /**
     * Llama al endpoint para obtener un número aleatorio entre 1 y 99.
     * La API retorna un array JSON (por ejemplo: [42]), así que parseamos
     * la respuesta a un List<Integer> y luego tomamos el primer (y único) elemento.
     */
    public Mono<Integer> getRandomNumber() {
        return webClient
                .get()
                // Como definimos baseUrl en el constructor, acá solo indicamos el path
                .uri("/random?min=1&max=99&count=1")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Integer>>() {})
                .doOnNext(numbers -> log.info("Número aleatorio recibido: {}", numbers.getFirst()))
                .map(List::getFirst); // Tomamos el primer elemento de la lista

    }
}

