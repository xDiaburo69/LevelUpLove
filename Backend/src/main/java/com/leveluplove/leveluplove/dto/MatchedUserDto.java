package com.leveluplove.leveluplove.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchedUserDto {
    private Long id;
    private String username;
    private String email;
    private Integer age;
    private String gender;
}
