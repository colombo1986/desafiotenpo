package org.tenpo.challenge.cvergara.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import org.tenpo.challenge.cvergara.model.entity.History;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface HistoryRepository extends R2dbcRepository<History, Long> {

    /**
     * Recupera todas las entradas de history con paginación.
     *
     * @param limit  Número de registros a recuperar.
     * @param offset Número de registros a saltar.
     * @return Flux<History> Un flujo reactivo de entradas paginadas.
     */
    @Query("SELECT * FROM history ORDER BY id LIMIT :limit OFFSET :offset")
    Flux<History> findAllWithPagination(int limit, int offset);

    /**
     * Cuenta el número total de entradas en la tabla history.
     *
     * @return Mono<Long> El número total de entradas.
     */
    @Query("SELECT COUNT(*) FROM history")
    Mono<Long> countAll();

}
