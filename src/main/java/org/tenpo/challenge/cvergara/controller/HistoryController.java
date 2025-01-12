package org.tenpo.challenge.cvergara.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tenpo.challenge.cvergara.exception.TooManyRequestsException;
import org.tenpo.challenge.cvergara.model.entity.History;
import org.tenpo.challenge.cvergara.model.dto.PaginatedResponse;
import org.tenpo.challenge.cvergara.service.HistoryService;
import reactor.core.publisher.Mono;


@RestController
public class HistoryController {

    HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }


    /**
     * Endpoint para obtener las entradas de history con paginación.
     *
     * @param page Número de página (0-based). Por defecto es 0.
     * @param size Tamaño de la página. Por defecto es 10.
     * @return Mono<PaginatedResponse<History>> La respuesta paginada.
     */
    @GetMapping("/history")
    @RateLimiter(name = "historyRateLimiter", fallbackMethod = "rateLimiterFallback")
    public Mono<PaginatedResponse<History>> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size <= 0) {
            return Mono.error(new IllegalArgumentException("Indice de pagina y size deben ser positivos."));
        }

        return historyService.getHistoryPaginated(page, size);
    }

    /**
     * Método de fallback para manejar solicitudes que exceden el límite de Rate Limiter.

     * @param page     Número de página solicitado.
     * @param size     Tamaño de la página solicitado.
     * @param ex       La excepción lanzada por el Rate Limiter.
     * @return Mono<PaginatedResponse<History>> Una respuesta vacía o con un mensaje de error.
     */
    public Mono<PaginatedResponse<History>> rateLimiterFallback(int page, int size, Throwable ex) {
        return Mono.error(new TooManyRequestsException("Se ha excedido el número máximo de solicitudes permitidas. Por favor, intenta nuevamente más tarde."));
    }
}
