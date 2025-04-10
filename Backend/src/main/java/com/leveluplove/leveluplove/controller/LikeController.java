package com.leveluplove.leveluplove.controller;

import com.leveluplove.leveluplove.security.UserPrincipal;
import com.leveluplove.leveluplove.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{targetUserId}")
    public ResponseEntity<Void> likeUser(@PathVariable Long targetUserId,
                                         Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long currentUserId = userPrincipal.getId();
        likeService.likeUser(currentUserId, targetUserId);
        return ResponseEntity.ok().build();
    }
}