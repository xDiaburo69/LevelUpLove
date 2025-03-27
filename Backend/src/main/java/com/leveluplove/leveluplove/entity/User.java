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

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Hauptschlüssel

    @NotBlank
    private String email; // ist required

    @NotBlank
    private String password; // ist required

    @NotBlank
    @Size(min = 3, max = 20)
    private String username; // kann man selbst eintragen

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; // wird beim Erstellen vom System angelegt

    @Enumerated(EnumType.STRING)
    private Roles role;

}

@Entity
@Table(name = "userprofile")
@Data
@NoArgsConstructor
@AllArgsConstructor
class UserProfile {
    @Id
    private Long id; // Ist benötigt, um die Beziehung zu User zu erstellen. @MapsId übernimmt den Wert von User.id

    @OneToOne // OneToOne Beziehung, da jeder User nur ein Profil hat
    @MapsId // MapsId übernimmt den Wert von User.id
    @JoinColumn(name = "id") // Verknüpft die User.id mit der UserProfile.id
    private User user; // ist required, da jeder User ein Profil hat

    @Size(min = 3, max = 20)
    private String name; // ist optional, kann man selbst eintragen

    @NotBlank
    private String gender; // ist required, wird Dropdown

    @Min(18)
    private Integer age; // ist required, durch Kalender auswählbar

    @NotBlank
    @Size(min = 3, max = 50)
    private String hometown; // ist required, kann man selbst eintragen
    private String typeOfLiving; // ist optional, wird Dropdown

    @ElementCollection
    @CollectionTable(name = "user_music", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "genre")
    private List<String> music = new ArrayList<>(); // ist optional, durch dropdown - mehrere markieren
    private Double height; // ist optional, wird Dropdown

    @Size(min = 3, max = 50)
    private String education; // ist optional, kann man selbst eintragen

    private String smoking; // ist optional, kann man selbst eintragen

    @Size(min = 3, max = 50)
    private String occupation; // ist optional, kann man selbst eintagen
    private String familyPlans; // ist optional, wird Dropdown

}