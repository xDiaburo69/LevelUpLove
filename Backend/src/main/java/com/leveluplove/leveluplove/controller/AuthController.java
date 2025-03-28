package com.leveluplove.leveluplove.controller;

import com.leveluplove.leveluplove.dto.UserRegistrationDto;
import com.leveluplove.leveluplove.dto.UserResponseDto;
import com.leveluplove.leveluplove.entity.Roles;
import com.leveluplove.leveluplove.entity.User;
import com.leveluplove.leveluplove.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        String hashedPassword = passwordEncoder.encode(registrationDto.getPassword());

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setUsername(registrationDto.getUsername());
        user.setPassword(hashedPassword);
        user.setGender(registrationDto.getGender());
        user.setRole(Roles.USER);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setEmail(user.getEmail());
        responseDto.setUsername(user.getUsername());
        responseDto.setGender(user.getGender());
        responseDto.setCreatedAt(user.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


}
