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
 * Service-Klasse für die Verwaltung von Benutzerprofilen
 * Bietet Funktionen zum Erstellen, Aktualisieren und Abrufen von Profildaten
 */
@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository; // Repository für Benutzerdaten

    @Autowired
    private UserProfileRepository userProfileRepository; // Repository für Profildaten

    /**
     * Erstellt ein neues Benutzerprofil oder aktualisiert ein bestehendes
     * 
     * @param userId ID des Benutzers
     * @param profileData Die neuen Profildaten
     * @return Das erstellte oder aktualisierte Benutzerprofil
     * @throws IllegalArgumentException wenn der Benutzer nicht gefunden wird
     */
    @Transactional
    public UserProfile createOrUpdateProfile(Long userId, UserProfile profileData) {
        // Benutzer anhand der ID suchen oder Exception werfen, wenn nicht gefunden
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Prüfen, ob bereits ein Profil für diesen Benutzer existiert
        Optional<UserProfile> existingProfile = userProfileRepository.findById(userId);
        UserProfile profile;

        if (existingProfile.isPresent()) {
            // Bestehendes Profil aktualisieren
            profile = existingProfile.get();
            // Nicht-null Eigenschaften aus den übergebenen Daten in das bestehende Profil kopieren
            if (profileData.getName() != null) profile.setName(profileData.getName());
            if (profileData.getBio() != null) profile.setBio(profileData.getBio());
            if (profileData.getHometown() != null) profile.setHometown(profileData.getHometown());
            if (profileData.getTypeOfLiving() != null) profile.setTypeOfLiving(profileData.getTypeOfLiving());
            if (profileData.getInterests() != null) profile.setInterests(profileData.getInterests());
            if (profileData.getMusic() != null) profile.setMusic(profileData.getMusic());
            if (profileData.getHeight() != null) profile.setHeight(profileData.getHeight());
            if (profileData.getEducation() != null) profile.setEducation(profileData.getEducation());
            if (profileData.getSmoking() != null) profile.setSmoking(profileData.getSmoking());
            if (profileData.getAlcohol() != null) profile.setAlcohol(profileData.getAlcohol());
            if (profileData.getOccupation() != null) profile.setOccupation(profileData.getOccupation());
            if (profileData.getFamilyPlans() != null) profile.setFamilyPlans(profileData.getFamilyPlans());
        } else {
            // Neues Profil erstellen
            profile = profileData;
            profile.setId(userId); // ID des Profils entspricht der Benutzer-ID
            profile.setUser(user); // Benutzer-Referenz setzen
        }

        // Profil speichern und zurückgeben
        return userProfileRepository.save(profile);
    }

    /**
     * Ruft das Benutzerprofil anhand der Benutzer-ID ab
     * 
     * @param userId ID des Benutzers
     * @return Optional mit dem Benutzerprofil oder leer, wenn nicht gefunden
     */
    @Transactional(readOnly = true)
    public Optional<UserProfile> getUserProfile(Long userId) {
        return userProfileRepository.findById(userId);
    }
}
