package org.tenpo.challenge.cvergara.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(name = "PaginatedResponse",
        description = "Dto para devolver el historial de llamadas a la API"
)
public class PaginatedResponse<T> {

    @Schema(description = "Lista que contiene los elementos de la página actual.")
    private List<T> content;

    @Schema(description = "Total de elementos disponibles en todas las páginas.")
    private long totalElements;

    @Schema(description = "Número de la página actual (basado en 0).")
    private int page;

    @Schema(description = "Cantidad de elementos por página.")
    private int size;

    @Schema(description = "Número total de páginas disponibles.")
    private int totalPages;

    public PaginatedResponse(List<T> content, long totalElements, int page, int size) {
        this.content = content;
        this.totalElements = totalElements;
        this.page = page;
        this.size = size;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
    }

}
