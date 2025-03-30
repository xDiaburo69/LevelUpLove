package com.leveluplove.leveluplove.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO für den Login-Request (Daten vom Client)
@Data // Lombok erzeugt Getter und Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank // E-Mail darf nicht leer sein
    @Email // Muss eine gültige E-Mail sein
    private String email;

    @NotBlank // Passwort darf nicht leer sein
    private String password;
}
