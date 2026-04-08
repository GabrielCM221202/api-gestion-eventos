package com.gestion.eventos.api.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeakerRequestDto {
    @NotBlank(message = "El nombre del ponente no puede ir vacio")
    @Size(max = 100, message = "El nombre del ponente no puede exceder los 100 caracteres")
    private String name;

    @NotBlank(message = "El email del ponente no puede ir vacio")
    @Email(message = "El formato de email no es valido")
    @Size(max = 100, message = "El email del ponente no puede exceder los 100 caracteres")
    private String email;
    @Size(max = 500, message = "La biografia del ponente no debe exceder los 500 caracteres")
    private String bio;
}
