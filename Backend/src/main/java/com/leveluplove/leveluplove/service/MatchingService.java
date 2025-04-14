package com.leveluplove.leveluplove.service;

import com.leveluplove.leveluplove.dto.MatchSuggestionDto;
import com.leveluplove.leveluplove.entity.UserProfile;
import com.leveluplove.leveluplove.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final UserProfileRepository userProfileRepository;

    public List<MatchSuggestionDto> getSuggestionsForUser(Long userId) {
        UserProfile currentProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profil nicht gefunden"));

        Set<String> currentInterests = currentProfile.getInterests();

        List<UserProfile> otherProfiles = userProfileRepository.findAllByUserIdNot(userId);

        return otherProfiles.stream()
                .map(profile -> {
                    Set<String> otherInterests = profile.getInterests();

                    Set<String> intersection = new HashSet<>(currentInterests);
                    intersection.retainAll(otherInterests);

                    Set<String> union = new HashSet<>(currentInterests);
                    union.addAll(otherInterests);

                    int score = union.isEmpty()
                            ? 0
                            : (int) ((intersection.size() * 100.0) / union.size());

                    return new MatchSuggestionDto(
                            profile.getUser().getId(),
                            profile.getUser().getUsername(),
                            profile.getUser().getAge(),
                            score,
                            intersection
                    );
                })
                .sorted(Comparator.comparingInt(MatchSuggestionDto::score).reversed())
                .collect(Collectors.toList());
    }
}
