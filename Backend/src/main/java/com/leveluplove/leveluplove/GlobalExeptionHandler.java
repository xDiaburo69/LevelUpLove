package com.leveluplove.leveluplove;

import com.leveluplove.leveluplove.util.ErrorResponseBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

// Diese Klasse fängt globale Fehler der API ab und wandelt sie in einheitliche API-Responses um
@ControllerAdvice
public class GlobalExeptionHandler {

    // Exception-Handler für Validation-Fehler (z.B. DTO Validierung)
    // Spring löst diese aus, wenn ein @Valid fehlschlägt (z.B. falsches Passwort-Format)
    @ExceptionHandler(MethodArgumentNotValidException.class) // Spezial-Exception, enthält eine Liste von FieldErrors
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) { // <?> = Generisch, da man je nach Handler unterschiedliche Rückgaben haben kann

        // Hier holt man sich alle Field-Fehler raus (z.B. "password" war ungültig) und baut sie als Liste von Maps
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(), // Name des ungültigen Feldes (z.B. "password")
                        "message", error.getDefaultMessage() // Fehlerbeschreibung (z.B. "muss mindestens 8 Zeichen haben")
                ))
                .toList();

        // Übergabe an den ErrorResponseBuilder für ein sauberes Fehler-JSON
        return ResponseEntity.badRequest()
                .body(ErrorResponseBuilder.build(400, "Bad Request", "Validation failed", errors));
    }

    // Exception-Handler, wenn z.B. eine Entität (User, Match, Message) nicht gefunden wird.
    // wird oft in Service- oder Repository-Layern verwendet
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex) {
        // Liste mit genau einem Fehler (allgemein)
        List<Map<String, String>> errors = List.of(Map.of("message", ex.getMessage())); // ex.getMessage() gibt den Fehlertext aus dem Code zurück
        return ResponseEntity.status(404).body(ErrorResponseBuilder.build(404, "Not Found", "Entity not found", errors));
    }

    // Exception-Handler für Authentifizierungsprobleme (z.B. fehlende oder falsche Zugangsdaten)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleUnauthorized(AuthenticationException ex) {
        // Auch hier nur eine allgemeine Fehlermeldung
        List<Map<String, String>> errors = List.of(Map.of("message", ex.getMessage())); // typischer Fehler: "Bad credentials"
        return ResponseEntity.status(401).body(ErrorResponseBuilder.build(401, "Unauthorized", "Authentication Error", errors));
    }

    // Exception-Handler, wenn ein Nutzer zwar authentifiziert, aber nicht autorisiert ist (z.B. kein Admin)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        // Ein einfacher Fehler, der mitteilt, dass der Zugriff verboten wurde
        List<Map<String, String>> errors = List.of(Map.of("message", ex.getMessage())); // Standard-Nachricht von Spring Security
        return ResponseEntity.status(403).body(ErrorResponseBuilder.build(403, "Access denied", "Access denied", errors));
    }
}
