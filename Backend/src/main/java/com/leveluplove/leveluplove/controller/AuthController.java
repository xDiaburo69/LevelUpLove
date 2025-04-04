package com.leveluplove.leveluplove.controller;

import com.leveluplove.leveluplove.dto.LoginDto;
import com.leveluplove.leveluplove.dto.LoginResponseDto;
import com.leveluplove.leveluplove.dto.UserRegistrationDto;
import com.leveluplove.leveluplove.dto.UserResponseDto;
import com.leveluplove.leveluplove.entity.Roles;
import com.leveluplove.leveluplove.entity.User;
import com.leveluplove.leveluplove.repository.UserRepository;
import com.leveluplove.leveluplove.service.JwtService;
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

    // Constructor Injection (Best Practice), um Abhängigkeiten bereitzustellen
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Registrierung eines neuen Users
    // POST = Neuer User wird erstellt
    // @Valid sorgt dafür, dass die DTO-Validierung automatisch geprüft wird
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationDto registrationDto) {

        // Passwort wird direkt beim Registrieren sicher gehashed (BCrypt)
        String hashedPassword = passwordEncoder.encode(registrationDto.getPassword());

        // Neues User-Objekt wird vorbereitet
        User user = new User();
        user.setEmail(registrationDto.getEmail()); // E-Mail aus DTO übernehmen
        user.setUsername(registrationDto.getUsername()); // Username aus DTO übernehmen
        user.setPassword(hashedPassword); // Gehashtes Passwort speichern
        user.setGender(registrationDto.getGender()); // Geschlecht aus DTO übernehmen
        user.setRole(Roles.USER); // Jeder registrierte Nutzer bekommt standardmäßig die Rolle USER
        user.setCreatedAt(LocalDateTime.now()); // Registrierungszeitpunkt speichern

        // User wird in der Datenbank gespeichert
        userRepository.save(user);

        // DTO für die Response vorbereiten (enthält KEIN Passwort)
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setEmail(user.getEmail());
        responseDto.setUsername(user.getUsername());
        responseDto.setGender(user.getGender());
        responseDto.setCreatedAt(user.getCreatedAt());

        // Rückgabe an den Client mit Status 201 (Created) und dem erstellten User ohne Passwort
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {

        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Prüfe Passwort (Klartext -> Hash-Vergleich
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // Geneiert Token wenn alles korrekt ist
        String jwt = jwtService.generateToken(user);

        // Gibt dem Client den Token zurück
        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }
}
