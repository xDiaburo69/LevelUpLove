package com.leveluplove.leveluplove.dto;

import java.util.Set;

public record MatchSuggestionDto(
        Long userId,
        String username,
        int age,
        int score,
        Set<String> sharedInterests
) {}
