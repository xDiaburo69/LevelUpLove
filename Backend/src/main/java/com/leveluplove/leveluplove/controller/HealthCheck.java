package com.leveluplove.leveluplove.controller;

import java.time.LocalDateTime;

// Warum Record? Record = eine schlanke Möglichkeit, immutable (unveränderliche) Objekte zu erstellen

// Der Record HealthCheck repräsentiert das Antwortobjekt für den /api/health Endpoint
// Records sind eine Kurzform in Java für einfache DTOs oder Datencontainer (automatisch Getter, Constructor, toString, equals, hashCode)
public record HealthCheck(String status, LocalDateTime timestamp, String dbStatus) { }
