package com.leveluplove.leveluplove.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // für API-Nutzung ohne Token
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/health").permitAll() // <-- das ist wichtig!
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // damit du nicht 403 bekommst

        return http.build();
    }
}
