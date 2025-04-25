package com.leveluplove.leveluplove.controller;

import com.leveluplove.leveluplove.entity.UserProfile;
import com.leveluplove.leveluplove.security.UserPrincipal;
import com.leveluplove.leveluplove.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/me/profile")
    public ResponseEntity<UserProfile> getMyProfile(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        Long userId = principal.getId();
        return userProfileService.getUserProfile(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/me/profile")
    public ResponseEntity<UserProfile> updateMyProfile(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody UserProfile profileData
    ) {
        Long userId = principal.getId();
        UserProfile updated = userProfileService.createOrUpdateProfile(userId, profileData);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable("id") Long userId) {
        try {
            Optional<UserProfile> profile = userProfileService.getUserProfile(userId);
            return profile
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch profile: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/profile")
    public ResponseEntity<?> createOrUpdateProfile(
            @PathVariable("id") Long userId,
            @Valid @RequestBody UserProfile profileData
    ) {
        try {
            UserProfile updatedProfile = userProfileService.createOrUpdateProfile(userId, profileData);
            return ResponseEntity.ok(updatedProfile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to save profile: " + e.getMessage()));
        }
    }
}