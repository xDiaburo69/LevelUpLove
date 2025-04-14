package com.leveluplove.leveluplove.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// UserProfile Entity (Profiltabelle)
// Hier liegen die persönlichen Profildaten
@Entity
@Table(name = "userprofile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id // Übernimmt die ID vom User (1:1 Beziehung)
    private Long id; // Fremdschlüssel zu User.id

    @OneToOne // Jeder User hat genau EIN Profil
    @MapsId // Nutzt dieselbe ID wie der zugehörigen User
    @JoinColumn(name = "id") // Verknüpfung über User.id
    private User user; // Referenz auf den zugehörigen User



    @Size(min = 3, max = 20)
    private String name; // Optional: Realname

    @Size(max = 500)
    private String bio;

    @NotBlank
    @Size(min = 3, max = 50)
    private String hometown; // Pflichtfeld: Wohnort

    private String typeOfLiving; // Optional (z.B. alleine, WG, mit Eltern)

    @ElementCollection
    @CollectionTable(name = "user_interests", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "interests")
    private Set<String> interests = new HashSet<>();

    @ElementCollection // JPA: Liste wird in einer Zwischentabelle gespeichert
    @CollectionTable(name = "user_music", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "genre")
    private List<String> music = new ArrayList<>(); // Optional: Liste von Lieblingsmusikrichtungen

    private Double height; // Optional: Größe

    @Size(min = 3, max = 50)
    private String education; // Optional: Ausbildung / Studium

    private String smoking; // Optional: Raucherstatus (ja/nein/gelegentlich)

    private String alcohol; // Optional: trinkt Alkohol (oft, gelegentlich, nie)

    @Size(min = 3, max = 50)
    private String occupation; // Optional: Beruf

    private String familyPlans; // Optional: Kinderwunsch etc.

    // Beziehung zu Profilbildern (ein Profil kann mehrere Bilder haben)
    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    // mappedBy: Referenziert das Feld in ProfileImage, das diese Beziehung verwaltet
    // cascade: Änderungen werden an verknüpfte Bilder weitergegeben (z.B. Löschen)
    // orphanRemoval: Bilder ohne Profilzuordnung werden automatisch gelöscht
    private List<ProfileImage> profileImages = new ArrayList<>();

    // Hilfsmethode zum einfachen Hinzufügen eines Bildes mit korrekter beidseitiger Verknüpfung
    public void addProfileImage(ProfileImage image) {
        // Check if maximum number of images would be exceeded
        if (profileImages.size() >= ProfileImage.MAX_IMAGES_PER_USER) {
            throw new IllegalStateException("Maximum of " + ProfileImage.MAX_IMAGES_PER_USER + " images per profile exceeded");
        }

        // Check if the file size exceeds the maximum allowed size
        if (image.getFileSize() != null && image.getFileSize() > ProfileImage.MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Image file size exceeds maximum allowed size of 400KB");
        }

        profileImages.add(image);
        image.setUserProfile(this); // Wichtig: Bidirektionale Beziehung pflegen
    }

    // Hilfsmethode zum sicheren Entfernen eines Bildes
    public void removeProfileImage(ProfileImage image) {
        profileImages.remove(image);
        image.setUserProfile(null); // Beziehung auf beiden Seiten auflösen
    }
}
