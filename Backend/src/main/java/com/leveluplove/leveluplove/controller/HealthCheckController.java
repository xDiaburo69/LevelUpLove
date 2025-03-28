package com.leveluplove.leveluplove.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

// Controller für den Health-Check-Endpunkt
// Dient dazu, den aktuellen Zustand der Anwendung und der Datenbank zu prüfen
@RestController
public class HealthCheckController {

    // DataSource wird von Spring automatisch bereitgestellt (Datenbankverbindung)
    @Autowired // Sagt Spring: Bitte gib mir die DataSource automatisch
    private DataSource dataSource;

    // Constructor Injection (Best Practice)
    // Ermöglicht einfachere Tests und bessere Lesbarkeit
    public HealthCheckController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // GET-Endpunkt unter /api/health
    // Antwortet mit Status der App + Status der Datenbank
    @GetMapping("/api/health")
    public HealthCheck healthCheck() {
        String dbStatus = "UNKNOWN"; // Standardwert

        // Hier wird geprüft, ob die Datenbankverbindung funktioniert
        try (Connection conn = dataSource.getConnection()) { // Holt sich eine Verbindung aus dem Connection-Pool

            // Ist die Connection innerhalb von 1 Sekunde gültig?
            if (conn.isValid(1)) {
                dbStatus = "UP"; // Datenbank erreichbar
            } else {
                dbStatus = "DOWN"; // Verbindung hergestellt, aber nicht gültig
            }

        } catch (SQLException e) {
            dbStatus = "ERROR"; // Es gab einen Fehler beim Verbinden
        }

        // Gibt den HealthCheck als JSON zurück (Record übernimmt die Struktur)
        return new HealthCheck("OK", LocalDateTime.now(), dbStatus);
    }
}

