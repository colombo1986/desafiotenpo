package org.tenpo.challenge.cvergara.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
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


}
