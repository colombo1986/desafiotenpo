package org.tenpo.challenge.cvergara.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("history")
public class History {

    @Id
    private Long id;

    private LocalDateTime insertdate;

    private String endpoint;

    private String parameters;

    @Column("response_status")
    private Integer responseStatus;

    @Column("response_body")
    private String responseBody;

    public History(String endpoint, String parameters) {
        this.endpoint = endpoint;
        this.parameters = parameters;
    }

    public History(String endpoint, String parameters, Integer responseStatus, String responseBody) {
        this.endpoint = endpoint;
        this.parameters = parameters;
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
    }
}
