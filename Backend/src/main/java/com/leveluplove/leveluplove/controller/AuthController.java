package com.leveluplove.leveluplove.controller;

import com.leveluplove.leveluplove.dto.LoginDto;
import com.leveluplove.leveluplove.dto.LoginResponseDto;
import com.leveluplove.leveluplove.dto.UserRegistrationDto;
import com.leveluplove.leveluplove.dto.UserResponseDto;
import com.leveluplove.leveluplove.entity.Roles;
import com.leveluplove.leveluplove.entity.User;
import com.leveluplove.leveluplove.entity.UserProfile;
import com.leveluplove.leveluplove.repository.UserRepository;
import com.leveluplove.leveluplove.service.JwtService;
import com.leveluplove.leveluplove.service.UserProfileService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

// Dieser Controller verwaltet alle Authentifizierungs-Endpunkte (z.B. Registrierung)
@RestController // Markiert die Klasse als REST-Controller (liefert JSON zurück)
@RequestMapping("/api/auth") // Alle Endpunkte beginnen mit /api/auth
public class AuthController {

    // Dependencies (Abhängigkeiten), die für die Registrierung benötigt werden
    private final UserRepository userRepository; // Für den Zugriff auf die User-Datenbank
    private final PasswordEncoder passwordEncoder; // Für das sichere Hashen der Passwörter
    private final JwtService jwtService;
    private final UserProfileService userProfileService;

    // Constructor Injection (Best Practice), um Abhängigkeiten bereitzustellen
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, UserProfileService userProfileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userProfileService = userProfileService;
    }

    // Registrierung eines neuen Users
    // POST = Neuer User wird erstellt
    // @Valid sorgt dafür, dass die DTO-Validierung automatisch geprüft wird
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationDto registrationDto) {

        // Passwort wird direkt beim Registrieren sicher gehashed (BCrypt)
        String hashedPw = passwordEncoder.encode(registrationDto.getPassword());

        // Neues User-Objekt wird vorbereitet
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setUsername(registrationDto.getUsername());
        user.setPassword(hashedPw);
        user.setGender(registrationDto.getGender());
        user.setBirthdate(registrationDto.getBirthdate());
        user.setRole(Roles.USER);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        // User wird in der Datenbank gespeichert
        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setUsername(registrationDto.getUsername());
        profile.setGender(registrationDto.getGender());
        profile.setBirthdate(registrationDto.getBirthdate());
        userProfileService.createProfile(profile);

        // DTO für die Response vorbereiten (enthält KEIN Passwort)
        UserResponseDto resp = new UserResponseDto();
        resp.setId(user.getId());
        resp.setEmail(user.getEmail());
        resp.setUsername(user.getUsername());
        resp.setGender(user.getGender());
        resp.setCreatedAt(user.getCreatedAt());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {

        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Prüfe Passwort (Klartext -> Hash-Vergleich
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // Generiert Token, wenn alles korrekt ist
        String jwt = jwtService.generateToken(user);

        // Gibt dem Client den Token zurück
        return ResponseEntity.ok(new LoginResponseDto(user.getUsername(), jwt));
    }
}
