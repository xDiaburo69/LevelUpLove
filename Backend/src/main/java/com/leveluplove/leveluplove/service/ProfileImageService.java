package com.leveluplove.leveluplove.service;

import com.leveluplove.leveluplove.entity.ProfileImage;
import com.leveluplove.leveluplove.entity.UserProfile;
import com.leveluplove.leveluplove.repository.ProfileImageRepository;
import com.leveluplove.leveluplove.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service-Klasse zur Verwaltung von Profilbildern
 * Bietet Funktionen zum Hochladen, Löschen und Verwalten von Benutzerbildern
 */
@Service
public class ProfileImageService {
    
    // Dependency Injection der benötigten Repositories
    @Autowired
    private UserProfileRepository userProfileRepository; // Repository für Benutzerprofildaten
    
    @Autowired
    private ProfileImageRepository profileImageRepository; // Repository für Profilbilddaten
    
    /**
     * Lädt ein neues Profilbild für einen Benutzer hoch
     * 
     * @param userId ID des Benutzers, für den das Bild hochgeladen wird
     * @param file Die hochgeladene Datei (MultipartFile)
     * @return Das neu erstellte ProfileImage-Objekt
     * @throws IOException Bei Fehlern beim Verarbeiten der Datei
     */
    @Transactional
    public ProfileImage uploadProfileImage(Long userId, MultipartFile file) throws IOException {
        // Benutzer anhand der ID suchen oder Exception werfen, wenn nicht gefunden
        UserProfile userProfile = userProfileRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User profile not found with ID: " + userId));
        
        // Überprüfung des Dateityps - nur Bilddateien erlaubt
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }
        
        // Neues ProfileImage-Objekt mit den Daten der hochgeladenen Datei erstellen
        ProfileImage newImage = new ProfileImage(
            userProfile,
            file.getOriginalFilename(),
            file.getContentType(),
            file.getSize(),
            file.getBytes() // Die Binärdaten des Bildes werden direkt in der Datenbank gespeichert
        );
        
        // Hilfsmethode nutzen, um das Bild dem Profil hinzuzufügen und die Beziehung zu verwalten
        try {
            userProfile.addProfileImage(newImage);
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new RuntimeException("Failed to add image: " + e.getMessage());
        }
        
        // Wenn es das erste Bild ist, als Profilbild festlegen
        if (userProfile.getProfileImages().size() == 1) {
            newImage.setProfilePicture(true);
        }
        
        // Aktualisiertes Benutzerprofil speichern
        userProfileRepository.save(userProfile);
        
        return newImage;
    }
    
    /**
     * Löscht ein Profilbild eines Benutzers
     * 
     * @param userId ID des Benutzers
     * @param imageId ID des zu löschenden Bildes
     */
    @Transactional
    public void deleteProfileImage(Long userId, Long imageId) {
        // Benutzer anhand der ID suchen oder Exception werfen, wenn nicht gefunden
        UserProfile userProfile = userProfileRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User profile not found with ID: " + userId));
            
        // Bild anhand der ID suchen oder Exception werfen, wenn nicht gefunden
        ProfileImage imageToDelete = profileImageRepository.findById(imageId)
            .orElseThrow(() -> new RuntimeException("Image not found with ID: " + imageId));
        
        // Überprüfen, ob das Bild diesem Benutzerprofil gehört
        if (!imageToDelete.getUserProfile().getId().equals(userId)) {
            throw new IllegalArgumentException("Image does not belong to this user profile");
        }
            
        // Status speichern, ob es sich um das Profilbild handelte
        boolean wasProfilePicture = imageToDelete.isProfilePicture();
        
        // Hilfsmethode nutzen, um das Bild korrekt zu entfernen
        userProfile.removeProfileImage(imageToDelete);
        
        // Wenn das gelöschte Bild das Profilbild war und weitere Bilder vorhanden sind,
        // das erste verfügbare Bild als neues Profilbild festlegen
        if (wasProfilePicture && !userProfile.getProfileImages().isEmpty()) {
            userProfile.getProfileImages().get(0).setProfilePicture(true);
        }
        
        // Aktualisiertes Benutzerprofil speichern
        userProfileRepository.save(userProfile);
    }
    
    /**
     * Gibt alle Profilbilder eines Benutzers zurück
     * 
     * @param userId ID des Benutzers
     * @return Liste aller Profilbilder des Benutzers
     */
    @Transactional(readOnly = true)
    public List<ProfileImage> getUserImages(Long userId) {
        return profileImageRepository.findByUserProfileId(userId);
    }
    
    /**
     * Gibt das aktuelle Profilbild eines Benutzers zurück
     * 
     * @param userId ID des Benutzers
     * @return Das aktuelle Profilbild oder null, wenn keines vorhanden
     */
    @Transactional(readOnly = true)
    public ProfileImage getProfilePicture(Long userId) {
        return profileImageRepository.findByUserProfileIdAndIsProfilePictureTrue(userId);
    }
    
    /**
     * Sucht ein Bild anhand seiner ID
     * 
     * @param imageId ID des gesuchten Bildes
     * @return Optional mit dem Bild oder leer, wenn nicht gefunden
     */
    @Transactional(readOnly = true)
    public Optional<ProfileImage> getImageById(Long imageId) {
        return profileImageRepository.findById(imageId);
    }
    
    /**
     * Legt ein bestimmtes Bild als Profilbild fest
     * 
     * @param userId ID des Benutzers
     * @param imageId ID des Bildes, das als Profilbild festgelegt werden soll
     * @return Das aktualisierte Profilbild
     */
    @Transactional
    public ProfileImage setAsProfilePicture(Long userId, Long imageId) {
        // Benutzer anhand der ID suchen oder Exception werfen, wenn nicht gefunden
        UserProfile userProfile = userProfileRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User profile not found with ID: " + userId));
            
        // Bild anhand der ID suchen oder Exception werfen, wenn nicht gefunden
        ProfileImage newProfilePic = profileImageRepository.findById(imageId)
            .orElseThrow(() -> new RuntimeException("Image not found with ID: " + imageId));
            
        // Überprüfen, ob das Bild diesem Benutzerprofil gehört
        if (!newProfilePic.getUserProfile().getId().equals(userId)) {
            throw new IllegalArgumentException("Image does not belong to this user profile");
        }
        
        // Aktuelles Profilbild zurücksetzen, falls vorhanden
        ProfileImage currentProfilePic = profileImageRepository.findByUserProfileIdAndIsProfilePictureTrue(userId);
        if (currentProfilePic != null) {
            currentProfilePic.setProfilePicture(false);
            profileImageRepository.save(currentProfilePic);
        }
        
        // Neues Profilbild festlegen und speichern
        newProfilePic.setProfilePicture(true);
        return profileImageRepository.save(newProfilePic);
    }
}
