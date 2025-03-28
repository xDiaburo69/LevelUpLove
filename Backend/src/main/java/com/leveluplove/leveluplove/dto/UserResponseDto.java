package com.leveluplove.leveluplove.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;
    private String gender;
    private LocalDateTime createdAt;
}
