package com.leveluplove.leveluplove.repository;

import com.leveluplove.leveluplove.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    // Diese beiden sind korrekt und ausreichend für beide Richtungen:
    Optional<Match> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
    List<Match> findByUser1IdOrUser2Id(Long userId1, Long userId2);
}
