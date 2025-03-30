package com.leveluplove.leveluplove.repository;

import com.leveluplove.leveluplove.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository-Interface für die User-Entity
// Gibt dir alle Standard-Datenbankmethoden (findById, findAll, save, delete, usw.) kostenlos von Spring JPA

public interface UserRepository extends JpaRepository<User, Long> {
    // User = Zu welcher Entity gehört dieses Repository?
    // Long = Datentyp des Primärschlüssels (id)
}
