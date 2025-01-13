package org.tenpo.challenge.cvergara.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {

    @Schema(description = "Codigo de status Http")
    private String statusCode;

    @Schema(description = "Descripcion")
    private String statusMsg;

    @Schema(description = "Contenido de la respuesta")
    private Integer content;

}