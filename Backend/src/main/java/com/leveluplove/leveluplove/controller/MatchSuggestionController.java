package com.leveluplove.leveluplove.controller;

import com.leveluplove.leveluplove.dto.MatchSuggestionDto;
import com.leveluplove.leveluplove.security.UserPrincipal;
import com.leveluplove.leveluplove.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MatchSuggestionController {

    private final MatchingService matchingService;

    @GetMapping("/suggestions")
    public ResponseEntity<List<MatchSuggestionDto>> getSuggestions(@AuthenticationPrincipal UserPrincipal user) {
        List<MatchSuggestionDto> suggestions = matchingService.getSuggestionsForUser(user.getId());
        return ResponseEntity.ok(suggestions);
    }
}
