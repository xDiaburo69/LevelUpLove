package com.leveluplove.leveluplove.config;

import com.leveluplove.leveluplove.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// Diese Klasse konfiguriert die Sicherheitseinstellungen (Security) für alle API-Endpunkte
@Configuration // Kennzeichnet diese Klasse als Konfigurationsklasse (Spring Boot erkennt das automatisch)
@EnableWebSecurity // Aktiviert Spring Security für die gesamte Anwendung
public class SecurityConfig {

    // Definiert den Security-Filter für alle HTTP-Anfragen
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                // Deaktiviert CSRF-Schutz (Cross-Site Request Forgery)
                // Grund: Da wir eine REST-API bauen, brauchen wir kein CSRF, da wir keinen Browser-Login-Form-Flow nutzen
                .csrf(csrf -> csrf.disable())

                // Hier definiert man, welche Anfragen ohne Authentifizierung erlaubt sind
                .authorizeHttpRequests(auth -> auth

                        // Alle User dürfen sich registrieren und den Health-Check aufrufen
                        .requestMatchers("/api/health", "/api/auth/**").permitAll()

                        // Alle anderen Anfragen müssen authentifiziert sein
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // JWT Filter registrieren

                // Aktiviert Basic-Auth für einfache Tests (Username/Password)
                // Achtung: Basic-Auth ist nur für Entwicklung / interne APIs sinnvoll
                .httpBasic(Customizer.withDefaults());

        // Gibt die Security-Konfiguration zurück
        return http.build();
    }

    // Definiert den PasswordEncoder
    // BCrypt verschlüsselt die Passwörter, damit sie sicher in der Datenbank gespeichert werden
    // Wird später beim User-Registrieren und Login verwendet
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

