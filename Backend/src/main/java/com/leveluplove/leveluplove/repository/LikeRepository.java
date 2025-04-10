package com.leveluplove.leveluplove.repository;

import com.leveluplove.leveluplove.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByLikerIdAndLikedId(Long likerId, Long likedId); // Überprüfen, ob ein Like existiert
    Optional<Like> findByLikerIdAndLikedId(Long likerId, Long likedId);
}
