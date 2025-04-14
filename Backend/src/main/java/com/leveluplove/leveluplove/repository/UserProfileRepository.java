package com.leveluplove.leveluplove.repository;

import com.leveluplove.leveluplove.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Repository-Interface für die UserProfile-Entity
// Ermöglicht Standard-Datenbankoperationen (findById, findAll, save, delete, usw.)
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUserId(Long userId);

    List<UserProfile> findAllByUserIdNot(Long userId);
}