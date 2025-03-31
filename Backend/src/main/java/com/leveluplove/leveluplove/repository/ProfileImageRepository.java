package com.leveluplove.leveluplove.repository;

import com.leveluplove.leveluplove.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository-Interface für die ProfileImage-Entity
// Bietet Standard-Datenbankoperationen für Profilbilder
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    // ProfileImage = Entity für dieses Repository
    // Long = Datentyp des Primärschlüssels (id)
    
    // Finde alle Bilder eines bestimmten Benutzerprofils
    List<ProfileImage> findByUserProfileId(Long userProfileId);
    
    // Finde das aktuelle Profilbild eines Benutzers
    ProfileImage findByUserProfileIdAndIsProfilePictureTrue(Long userProfileId);
}
