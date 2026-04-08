package com.gestion.eventos.api.dto;

import com.gestion.eventos.api.domain.Speaker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Schema(description = "Detalles de la solicitud oara crear/actualizar un evento")
public class EventRequestDto {
    @Schema(description = "Nombre del evento" , example = "Curso de Spring Security")
    @NotBlank(message = "El nombre del evento no puede estar vacio")
    private String name;

    @NotNull(message = "La fecha del evento no puede estar vacia")
    private LocalDate date;

    @NotBlank(message = "La ubicación del evento no puede estar vacio")
    private String location;

    @NotNull(message = "La categoria del evento es obligatiria")
    private Long categoryId;

    private Set<Long> speakersIds;

}
