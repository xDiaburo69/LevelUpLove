package com.leveluplove.leveluplove.util;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Hilfsklasse, um alle Error-Responses einheitlich aufzubauen (Builder-Pattern)
public class ErrorResponseBuilder {

    // Static-Methoden sind ohne Objekt nutzbar (Utility-Klasse)
    // Erstellt eine Map, die später direkt als JSON an den Client zurückgegeben wird
    // Warum Map<String, Object>? → Weil man verschiedene Typen (String, int, List, UUID) in einer Map mischen will
    // Warum List<Map<String, String>> errors? → Weil es eine Liste von kleinen Error-Objekten ist (z.B. Feldname + Fehlerbeschreibung)
    public static Map<String, Object> build(int status, String error, String message, List<Map<String, String>> errors) {

        // LinkedHashMap, damit die Reihenfolge der Keys im JSON später immer gleich bleibt
        Map<String, Object> response = new LinkedHashMap<>();

        // Generiert eine zufällige Error-ID (UUID) damit Fehler später in Logs leichter gefunden werden können
        response.put("errorId", UUID.randomUUID().toString());

        // Fügt den aktuellen Zeitpunkt (in UTC) ein, wann der Fehler passiert ist
        response.put("timestamp", Instant.now());

        // Fügt den HTTP-Statuscode hinzu (z.B. 400, 404, 500)
        response.put("status", status);

        // Der Name des Fehlers (z.B. Bad Request, Not Found)
        response.put("error", error);

        // Eine allgemeine Nachricht, z.B. "Validation failed"
        response.put("message", message);

        // Liste von Detail-Fehlern, wenn es mehrere gibt (z.B. bei Validation mehrere Felder)
        response.put("errors", errors);

        // Gibt die fertige Map zurück, die direkt als JSON ausgegeben werden kann
        return response;
    }

}

