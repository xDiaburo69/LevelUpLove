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

    @CreationTimestamp // Wird automatisch beim Erstellen gesetzt
    @Column(updatable = false) // Darf später nicht mehr geändert werden
    private LocalDateTime createdAt; // Zeitstempel, wann der Account erstellt wurde

    @Enumerated(EnumType.STRING) // Speichert den Enum-Namen als String (z.B. "USER", "ADMIN")
    private Roles role; // Rolle des Users
}


// UserProfile Entity (Profiltabelle)
// Hier liegen die persönlichen Profildaten
@Entity
@Table(name = "userprofile")
@Data
@NoArgsConstructor
@AllArgsConstructor
class UserProfile {

    @Id // Übernimmt die ID vom User (1:1 Beziehung)
    private Long id; // Fremdschlüssel zu User.id

    @OneToOne // Jeder User hat genau EIN Profil
    @MapsId // Nutzt dieselbe ID wie der zugehörige User
    @JoinColumn(name = "id") // Verknüpfung über User.id
    private User user; // Referenz auf den zugehörigen User

    @Size(min = 3, max = 20)
    private String name; // Optional: Realname

    @Min(18) // Mindestalter 18 Jahre
    private Integer age; // Pflichtfeld (wird meist über Kalender im Frontend ausgewählt)

    @NotBlank
    @Size(min = 3, max = 50)
    private String hometown; // Pflichtfeld: Wohnort

    private String typeOfLiving; // Optional (z.B. alleine, WG, mit Eltern)

    @ElementCollection // JPA: Liste wird in einer Zwischentabelle gespeichert
    @CollectionTable(name = "user_music", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "genre")
    private List<String> music = new ArrayList<>(); // Optional: Liste von Lieblingsmusikrichtungen

    private Double height; // Optional: Größe

    @Size(min = 3, max = 50)
    private String education; // Optional: Ausbildung / Studium

    private String smoking; // Optional: Raucherstatus (ja/nein/gelegentlich)

    @Size(min = 3, max = 50)
    private String occupation; // Optional: Beruf

    private String familyPlans; // Optional: Kinderwunsch etc.
}
