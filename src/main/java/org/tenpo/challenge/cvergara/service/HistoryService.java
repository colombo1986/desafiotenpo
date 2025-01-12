package org.tenpo.challenge.cvergara.service;

import org.springframework.stereotype.Service;
import org.tenpo.challenge.cvergara.model.entity.History;
import org.tenpo.challenge.cvergara.model.dto.PaginatedResponse;
import org.tenpo.challenge.cvergara.repository.HistoryRepository;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }


    /**
     * Recupera las entradas de history con paginación.
     *
     * @param page Número de página (0-based).
     * @param size Tamaño de la página.
     * @return Mono<PaginatedResponse<History>> La respuesta paginada.
     */
    public Mono<PaginatedResponse<History>> getHistoryPaginated(int page, int size) {
        int offset = page * size;

        Mono<Long> totalElementsMono = historyRepository.countAll();
        Mono<List<History>> contentMono = historyRepository.findAllWithPagination(size, offset).collectList();

        return Mono.zip(totalElementsMono, contentMono)
                .map(tuple -> {
                    long totalElements = tuple.getT1();
                    List<History> content = tuple.getT2();
                    return new PaginatedResponse<>(content, totalElements, page, size);
                });
    }
}
