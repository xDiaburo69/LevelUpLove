package com.leveluplove.leveluplove.service;

import com.leveluplove.leveluplove.entity.User;
import com.leveluplove.leveluplove.entity.UserProfile;
import com.leveluplove.leveluplove.repository.UserProfileRepository;
import com.leveluplove.leveluplove.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service-Klasse für die Verwaltung von Benutzerprofilen.
 * Hier legen wir Profile an oder aktualisieren bestehende.
 */
@Service
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    /**
     * Konstruktor-Injektion aller benötigten Repositories.
     */
    @Autowired
    public UserProfileService(UserRepository userRepository,
                              UserProfileRepository userProfileRepository) {
        this.userRepository        = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    /**
     * Legt ein neues Benutzerprofil an.
     * Wird direkt nach dem Anlegen eines Users aufgerufen.
     *
     * @param profile Das Profil-Objekt, das bereits Felder wie user, username, gender und birthdate gesetzt hat.
     * @return Das gespeicherte UserProfile.
     */
    public UserProfile createProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    /**
     * Erstellt ein neues Profil oder aktualisiert ein bestehendes.
     * Kopiert nur nicht-null-Felder aus profileData in das existierende Profil.
     *
     * @param userId      ID des Benutzers, zu dem das Profil gehört.
     * @param profileData Profildaten mit den Werten, die gesetzt werden sollen.
     * @return Das neu erstellte oder aktualisierte Profil.
     * @throws IllegalArgumentException wenn kein User mit der angegebenen ID existiert.
     */
    @Transactional
    public UserProfile createOrUpdateProfile(Long userId, UserProfile profileData) {
        // 1) Sicherstellen, dass der User existiert
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User nicht gefunden mit ID: " + userId));

        // 2) Prüfen, ob schon ein Profil existiert
        Optional<UserProfile> optProfile = userProfileRepository.findById(userId);
        UserProfile profile;
        if (optProfile.isPresent()) {
            // Profil aktualisieren: nur Felder überschreiben, die profileData nicht-null sind
            profile = optProfile.get();
            if (profileData.getUsername()  != null) profile.setUsername(profileData.getUsername());
            if (profileData.getGender()    != null) profile.setGender(profileData.getGender());
            if (profileData.getBirthdate() != null) profile.setBirthdate(profileData.getBirthdate());
            // hier bei Bedarf weitere Felder hinzufügen...
        } else {
            // Neues Profil anlegen
            profile = profileData;
            profile.setId(userId);    // @MapsId syncs ID mit dem User
            profile.setUser(user);    // Verknüpfung zum User
        }

        // 3) Speichern und zurückgeben
        return userProfileRepository.save(profile);
    }

    /**
     * Liefert das Profil eines Nutzers, falls vorhanden.
     *
     * @param userId ID des Users.
     * @return Optional mit dem Profil oder leer, falls keines existiert.
     */
    @Transactional(readOnly = true)
    public Optional<UserProfile> getUserProfile(Long userId) {
        return userProfileRepository.findById(userId);
    }
}
