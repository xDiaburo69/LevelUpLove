package com.leveluplove.leveluplove.repository;

import com.leveluplove.leveluplove.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository-Interface für die UserProfile-Entity
// Ermöglicht Standard-Datenbankoperationen (findById, findAll, save, delete, usw.)
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // UserProfile = Entity für dieses Repository
    // Long = Datentyp des Primärschlüssels (id)
}
