package org.tenpo.challenge.cvergara.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tenpo.challenge.cvergara.exception.TooManyRequestsException;
import org.tenpo.challenge.cvergara.model.dto.PaginatedResponse;
import org.tenpo.challenge.cvergara.model.entity.History;
import org.tenpo.challenge.cvergara.service.AddService;
import reactor.core.publisher.Mono;


@RestController
public class AddController {


     AddService addService;

    public AddController(AddService addService) {
        this.addService = addService;
    }

    @PostMapping("/add")
    @RateLimiter(name = "addRateLimiter", fallbackMethod = "rateLimiterFallback")
    public Mono<Integer> add(@RequestParam Integer num1, @RequestParam Integer num2) {

        return addService.addNumbers(num1, num2);
    }

    /**
     * Método de fallback para manejar solicitudes que exceden el límite de Rate Limiter.
     *
     * @param num1 Número 1 recibido en la solicitud original.
     * @param num2 Número 2 recibido en la solicitud original.
     * @param ex   La excepción lanzada por el Rate Limiter.
     * @return Mono<Integer> con un mensaje de error.
     */
    public Mono<Integer> rateLimiterFallback(Integer num1, Integer num2, Throwable ex) {
        return Mono.error(new TooManyRequestsException("Se ha excedido el número máximo de solicitudes permitidas. Por favor, intenta nuevamente más tarde."));
    }

}
