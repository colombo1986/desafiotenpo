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

    public Mono<Boolean> saveNumber(Integer number) {
        // Guardamos el número como String
        // "myNumber" es la key en Redis
        // Añadimos un TTL de 30 minutos
        return redisTemplate
                .opsForValue()
                .set("myNumber", String.valueOf(number), Duration.ofMinutes(30));
    }

}
