package org.tenpo.challenge.cvergara.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.tenpo.challenge.cvergara.model.dto.ResponseDto;

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
    public Mono<ResponseDto> add(@RequestParam Integer num1, @RequestParam Integer num2) {

        if (num1 == null || num2 == null || num1 == 0 || num2 == 0) {
            return Mono.just(new ResponseDto(
                    "400",
                    "Los parámetros num1 y num2 no pueden ser nulos ni ceros.",
                    null
            ));
        }

        // Llamar al servicio y construir la respuesta en caso de éxito
        return addService.addNumbers(num1, num2)
                .map(result -> new ResponseDto(
                        "200",
                        "Operación exitosa",
                        result
                ))
                .onErrorResume(error -> {
                    // Manejar errores inesperados
                    return Mono.just(new ResponseDto(
                            "500",
                            "Error interno del servidor: " + error.getMessage(),
                            null
                    ));
                });
    }

    /**
     * Método de fallback para manejar solicitudes que exceden el límite de Rate Limiter.
     *
     * @param num1 Número 1 recibido en la solicitud original.
     * @param num2 Número 2 recibido en la solicitud original.
     * @param ex   La excepción lanzada por el Rate Limiter.
     * @return Mono<Integer> con un mensaje de error.
     */
    public Mono<ResponseDto> rateLimiterFallback(Integer num1, Integer num2, Throwable ex) {
        return Mono.just(new ResponseDto(
                "429",
                "Se ha excedido el límite de solicitudes. Intente nuevamente más tarde.",
                null
        ));
    }

}
