package com.leveluplove.leveluplove.controller;

import com.leveluplove.leveluplove.entity.ProfileImage;
import com.leveluplove.leveluplove.service.ProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * REST-Controller zur Verwaltung des Profilbildes
 * Bietet Endpunkte zum Hochladen, Abrufen und Löschen des Benutzerbildes
 */
@RestController
@RequestMapping("/api/users/{userId}/profile-image") // Basis-URL für alle Endpunkte dieses Controllers
public class ProfileImageController {

    @Autowired
    private ProfileImageService profileImageService; // Verweis auf den Service für die Geschäftslogik

    /**
     * Endpunkt zum Hochladen eines neuen Profilbildes
     * 
     * @param userId ID des Benutzers
     * @param file Die hochzuladende Bilddatei
     * @return Erfolgsmeldung mit der ID des neuen Bildes oder Fehlermeldung
     */
    @PostMapping
    public ResponseEntity<?> uploadProfileImage(@PathVariable Long userId, 
                                      @RequestParam("file") MultipartFile file) {
        try {
            // Bild über den Service hochladen und als Profilbild festlegen
            ProfileImage saved = profileImageService.uploadProfileImage(userId, file);
            profileImageService.setAsProfilePicture(userId, saved.getId());
            // Erfolgsantwort mit Bild-ID zurückgeben
            return ResponseEntity.ok(Map.of(
                "message", "Profile image uploaded and set successfully",
                "imageId", saved.getId()
            ));
        } catch (IllegalArgumentException e) {
            // Fehlerbehandlung für ungültige Eingaben (z.B. falsches Dateiformat)
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            // Fehlerbehandlung für Probleme beim Verarbeiten der Datei
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to process image: " + e.getMessage()));
        } catch (Exception e) {
            // Allgemeine Fehlerbehandlung für unerwartete Fehler
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred: " + e.getMessage()));
        }
    }

    /**
     * Endpunkt zum Abrufen des aktuellen Profilbildes eines Benutzers
     * 
     * @param userId ID des Benutzers
     * @return Das aktuelle Profilbild oder 404, wenn keines vorhanden
     */
    @GetMapping
    public ResponseEntity<?> getProfileImage(@PathVariable Long userId) {
        try {
            ProfileImage profilePic = profileImageService.getProfilePicture(userId);
            
            // Überprüfen, ob ein Profilbild existiert
            if (profilePic == null) {
                return ResponseEntity.notFound().build();
            }
            
            // HTTP-Header für den richtigen Content-Type (MIME) setzen
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(profilePic.getFileType()));

            // Binärdaten des Bildes mit entsprechenden Headern zurückgeben
            return new ResponseEntity<>(profilePic.getImageData(), headers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Fehlerbehandlung für ungültige Eingaben
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Allgemeine Fehlerbehandlung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve profile image: " + e.getMessage()));
        }
    }

    /**
     * Endpunkt zum Löschen des Profilbildes
     * 
     * @param userId ID des Benutzers
     * @return Erfolgsmeldung oder Fehlermeldung
     */
    @DeleteMapping
    public ResponseEntity<?> deleteProfileImage(@PathVariable Long userId) {
        try {
            ProfileImage profilePic = profileImageService.getProfilePicture(userId);
            if (profilePic == null) {
                return ResponseEntity.notFound().build();
            }

            profileImageService.deleteProfileImage(userId, profilePic.getId());
            return ResponseEntity.ok(Map.of("message", "Profile image deleted successfully"));
        } catch (IllegalArgumentException e) {
            // Fehlerbehandlung für ungültige Eingaben
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Allgemeine Fehlerbehandlung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete profile image: " + e.getMessage()));
        }
    }
}
