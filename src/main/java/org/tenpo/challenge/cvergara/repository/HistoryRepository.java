package org.tenpo.challenge.cvergara.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import org.tenpo.challenge.cvergara.model.History;


@Repository
public interface HistoryRepository extends R2dbcRepository<History, Long> {


}
