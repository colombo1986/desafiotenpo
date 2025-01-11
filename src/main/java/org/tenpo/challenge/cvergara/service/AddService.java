package org.tenpo.challenge.cvergara.service;


import org.springframework.stereotype.Service;
import org.tenpo.challenge.cvergara.client.RandomNumberClient;
import reactor.core.publisher.Mono;

@Service
public class AddService {

    private final RandomNumberClient randomNumberClient;

    public AddService(RandomNumberClient randomNumberClient) {
        this.randomNumberClient = randomNumberClient;
    }

    public Mono<Integer> addNumbers(Integer num1 , Integer num2){

        int sum = num1 + num2;

        return randomNumberClient.getRandomNumber()
                .map(random -> {
                    // random es el porcentaje (ej. 10 => 10%)
                    double total = sum + (sum * (random / 100.0));
                    // Convertimos a entero
                    return (int) Math.round(total);
                });
    }
}
