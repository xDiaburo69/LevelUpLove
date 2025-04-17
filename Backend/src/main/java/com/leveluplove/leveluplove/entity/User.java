package com.leveluplove.leveluplove.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * User Entity: speichert Kontoinformationen.
 * Das Alter wird nicht mehr persistiert, sondern
 * bei jedem Zugriff aus birthdate berechnet.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    private String gender;

    /**
     * Geburtsdatum speichern, um Alter zu berechnen.
     * Muss bei der Registrierung übermittelt werden.
     */
    @NotNull(message = "Birthdate can't be empty.")
    @Past(message = "Birthdate has to be in the past.")
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(nullable = false)
    private LocalDate birthdate;

    /**
     * Erstellungszeitstempel des Accounts.
     * Wird automatisch gesetzt und nicht mehr änderbar.
     */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Roles role;

    /**
     * Berechnet das Alter in Jahren aus birthdate.
     * @return Alter in Jahren oder null, falls birthdate fehlt
     */
    @Transient
    @JsonProperty("age")
    public Integer getAge() {
        if (birthdate == null) return null;
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}
