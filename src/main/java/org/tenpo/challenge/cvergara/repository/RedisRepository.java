package org.tenpo.challenge.cvergara.repository;


import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class RedisRepository {

   private final ReactiveStringRedisTemplate redisTemplate;

    public RedisRepository(ReactiveStringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Guarda el número con una TTL de 30 minutos en Redis.
     */
    public Mono<Boolean> saveNumber(Integer number) {
        String key = "randomNumber"; // clave fija
        String value = String.valueOf(number);
        return redisTemplate
                .opsForValue()
                .set(key, value, Duration.ofMinutes(30));
    }

    /**
     * Obtiene el número guardado en Redis si existe.
     * Retorna un Mono vacío si no existe o no hay valor.
     */
    public Mono<Integer> getLastSavedNumber() {
        String key = "randomNumber";
        return redisTemplate
                .opsForValue()
                .get(key)
                .flatMap(value -> {
                    try {
                        return Mono.just(Integer.parseInt(value));
                    } catch (NumberFormatException e) {
                        return Mono.empty();
                    }
                });
    }

}
