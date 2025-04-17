package com.leveluplove.leveluplove.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


// Data Transfer Object (DTO) für die Registrierung eines neuen Users
// Wird vom Client (z.B. Postman oder Frontend) an den Server geschickt

@Data // Lombok generiert automatisch Getter, Setter, toString, equals und hashCode
@NoArgsConstructor // Lombok generiert einen leeren (parameterlosen) Konstruktor
@AllArgsConstructor // Lombok generiert einen Konstruktor mit allen Feldern
public class UserRegistrationDto {

    // Pflichtfeld (darf nicht leer sein) und muss eine gültige E-Mail sein
    @NotBlank
    @Email
    private String email;

    // Pflichtfeld (darf nicht leer sein)
    @NotBlank
    private String username;

    // Pflichtfeld (darf nicht leer sein)
    // Muss mindestens:
    // - 8 Zeichen lang sein
    // - 1 Kleinbuchstaben
    // - 1 Großbuchstaben
    // - 1 Zahl
    // - 1 Sonderzeichen enthalten
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one special character, upper, lower letter and one number."
    )
    private String password;

    // Pflichtfeld (darf nicht leer sein)
    @NotBlank
    private String gender;

    @NotNull
    @Past(message = "Birthdate has to be in the past.")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthdate;
}
