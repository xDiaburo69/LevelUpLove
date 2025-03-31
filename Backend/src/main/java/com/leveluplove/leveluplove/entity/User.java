package com.leveluplove.leveluplove.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// User Entity (Benutzertabelle)
// Speichert alle notwendigen Informationen zum Account selbst (nicht zum Profil!)
@Entity // Markiert die Klasse als JPA-Entity (wird in der Datenbank als Tabelle angelegt)
@Table(name = "users") // Tabellenname in der Datenbank
@Data // Lombok: Erstellt automatisch Getter, Setter, toString, equals, hashCode
@NoArgsConstructor // Lombok: Erstellt einen leeren Konstruktor
@AllArgsConstructor // Lombok: Erstellt einen Konstruktor mit allen Attributen
public class User {

    @Id // Primärschlüssel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatische ID-Generierung (zählt von selbst hoch)
    private Long id; // Hauptschlüssel (primary key)

    @NotBlank // Pflichtfeld
    private String email; // Benutzer-E-Mail (wird auch zur Anmeldung genutzt)

    @NotBlank // Pflichtfeld
    private String password; // Gehashter Passwort-Hash (NICHT das Klartext-Passwort)

    @NotBlank // Pflichtfeld
    @Size(min = 3, max = 20) // Länge zwischen 3 und 20 Zeichen
    private String username; // Öffentlicher Username

    @NotBlank // Pflichtfeld
    private String gender; // Geschlecht (male, female, other, alien, ...)

    @Min(18) // Mindestalter 18 Jahre
    private Integer age; // Pflichtfeld (wird meist über Kalender im Frontend ausgewählt)

    @CreationTimestamp // Wird automatisch beim Erstellen gesetzt
    @Column(updatable = false) // Darf später nicht mehr geändert werden
    private LocalDateTime createdAt; // Zeitstempel, wann der Account erstellt wurde

    @Enumerated(EnumType.STRING) // Speichert den Enum-Namen als String (z.B. "USER", "ADMIN")
    private Roles role; // Rolle des Users
}

