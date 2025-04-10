package com.leveluplove.leveluplove.controller;

import com.leveluplove.leveluplove.dto.MatchedUserDto;
import com.leveluplove.leveluplove.entity.User;
import com.leveluplove.leveluplove.security.UserPrincipal;
import com.leveluplove.leveluplove.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchedUserDto>> getMatches(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<MatchedUserDto> matches = matchService.getMatchesForUser(userId);
        return ResponseEntity.ok(matches);
    }
}
