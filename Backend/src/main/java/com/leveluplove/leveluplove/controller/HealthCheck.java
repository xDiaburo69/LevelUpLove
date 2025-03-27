package com.leveluplove.leveluplove.controller;

import java.time.LocalDateTime;

public record HealthCheck(String status, LocalDateTime timestamp, String dbStatus) { }
