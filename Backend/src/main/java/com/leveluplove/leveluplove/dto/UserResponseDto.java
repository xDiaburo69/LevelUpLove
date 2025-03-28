package com.leveluplove.leveluplove.dto;

import lombok.Data;

import java.time.LocalDateTime;

// Data Transfer Object (DTO) für die API-Antwort nach der erfolgreichen Registrierung
// Enthält nur die Daten, die der Client sehen darf (kein Passwort!)

@Data // Lombok generiert automatisch Getter, Setter, toString, equals und hashCode
public class UserResponseDto {

    // ID des Users (wird von der Datenbank vergeben)
    private Long id;

    // E-Mail des Users (wird bei der Registrierung angegeben)
    private String email;

    // Username des Users
    private String username;

    // Geschlecht des Users
    private String gender;

    // Zeitpunkt der Erstellung des Accounts
    private LocalDateTime createdAt;
}

