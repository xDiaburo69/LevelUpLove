package com.leveluplove.leveluplove.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

@RestController
public class HealthCheckController {
    @Autowired
    private DataSource dataSource;

    public HealthCheckController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/api/health")
    public HealthCheck healthCheck() {
        String dbStatus = "UNKNOWN";
        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(1)) {
                dbStatus = "UP";
            } else {
                dbStatus = "DOWN";
            }
        } catch (SQLException e) {
            dbStatus = "ERROR";
        }

        return new HealthCheck("OK", LocalDateTime.now(), dbStatus);
    }
}
