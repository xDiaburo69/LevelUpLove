package com.leveluplove.leveluplove.service;

import com.leveluplove.leveluplove.dto.MatchedUserDto;
import com.leveluplove.leveluplove.entity.Match;
import com.leveluplove.leveluplove.entity.User;
import com.leveluplove.leveluplove.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public List<MatchedUserDto> getMatchesForUser(Long userId) {

        List<Match> matches = matchRepository.findByUser1IdOrUser2Id(userId, userId);

        return matches.stream()
                .map(match -> {
                    User matchedUser = match.getUser1().getId().equals(userId)
                            ? match.getUser2()
                            : match.getUser1();

                    return new MatchedUserDto(
                            matchedUser.getId(),
                            matchedUser.getUsername(),
                            matchedUser.getEmail(),
                            matchedUser.getAge(),
                            matchedUser.getGender()
                    );
                })
                .collect(Collectors.toList());
    }
}