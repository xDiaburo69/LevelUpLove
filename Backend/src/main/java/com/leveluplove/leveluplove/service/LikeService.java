package com.leveluplove.leveluplove.service;

import com.leveluplove.leveluplove.entity.Like;
import com.leveluplove.leveluplove.entity.Match;
import com.leveluplove.leveluplove.entity.User;
import com.leveluplove.leveluplove.repository.LikeRepository;
import com.leveluplove.leveluplove.repository.MatchRepository;
import com.leveluplove.leveluplove.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final MatchRepository matchRepository;

    public void likeUser(Long likerId, Long likedId) {
        // 1. Sich selbst liken ist verboten
        if (likerId.equals(likedId)) {
            throw new IllegalArgumentException("You cannot like yourself");
        }

        // 2. Nutzer existieren?
        User liker = userRepository.findById(likerId)
                .orElseThrow(() -> new EntityNotFoundException("Liker not found"));
        User liked = userRepository.findById(likedId)
                .orElseThrow(() -> new EntityNotFoundException("Liked user not found"));

        // 3. Hast du die Person schon geliket?
        if (likeRepository.existsByLikerIdAndLikedId(likerId, likedId)) {
            throw new IllegalStateException("You already liked this user");
        }

        // 4. Gibt es ein Gegen-Like? → MATCH!
        boolean isMatch = likeRepository.existsByLikerIdAndLikedId(likedId, likerId);

        if (isMatch) {
            // Prüfen ob Match schon existiert
            boolean alreadyMatched = matchRepository.findByUser1IdAndUser2Id(likerId, likedId)
                    .or(() -> matchRepository.findByUser1IdAndUser2Id(likedId, likerId))
                    .isPresent();

            if (!alreadyMatched) {
                Match match = new Match();
                match.setUser1(liker);
                match.setUser2(liked);
                match.setMatchedAt(LocalDateTime.now());
                matchRepository.save(match);

                System.out.println("💘 MATCH gespeichert zwischen " + liker.getUsername() + " & " + liked.getUsername());
            }
        }

        // 5. Like speichern
        Like like = new Like();
        like.setLiker(liker);
        like.setLiked(liked);
        like.setCreatedAt(LocalDateTime.now());

        likeRepository.save(like);
    }

    public void unlikeUser(Long likerId, Long likedId) {

        // Hast du die Person schon geliket?
        Like like = likeRepository.findByLikerIdAndLikedId(likerId, likedId)
                .orElseThrow(() -> new EntityNotFoundException("Like not found"));

        // Like löschen
        likeRepository.delete(like);

        // Wenn vorher ein Match bestand, dann löschen
        Optional<Match> match = matchRepository.findByUser1IdAndUser2Id(likerId, likedId)
                .or(() -> matchRepository.findByUser1IdAndUser2Id(likedId, likerId));

        match.ifPresent(matchRepository::delete);
    }
}

