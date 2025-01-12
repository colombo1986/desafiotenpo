package org.tenpo.challenge.cvergara;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class CvergaraApplication {

    public static void main(String[] args) {
        SpringApplication.run(CvergaraApplication.class, args);
    }

}
