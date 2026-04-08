package com.gestion.eventos.api.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Size(min = 4, max = 20, message = "El nombre de usuario debe tener entre 4 y 20 caracteres")
    private String username;
    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "Debe ser una dirección de correo electronico valida")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacia")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;


    private Set<String> roles;
}
