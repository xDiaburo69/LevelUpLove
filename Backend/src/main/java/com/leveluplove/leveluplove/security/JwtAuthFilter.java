package com.leveluplove.leveluplove.security;

import com.leveluplove.leveluplove.entity.Roles;
import com.leveluplove.leveluplove.repository.UserRepository;
import com.leveluplove.leveluplove.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = jwtService.extractAllClaims(token);

        Long id = ((Number) claims.get("id")).longValue();
        String email = claims.get("email", String.class);
        String roleString = claims.get("role", String.class);

        Roles role = Roles.USER; // Fallback-Rolle
        if (roleString != null) {
            try {
                role = Roles.valueOf(roleString);
            } catch (IllegalArgumentException ignored) {
                System.out.println("❗ Invalid role in the token, default to USER");
            }
        }

        UserPrincipal principal = new UserPrincipal(
                id,
                email,
                null,
                role
        );

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
