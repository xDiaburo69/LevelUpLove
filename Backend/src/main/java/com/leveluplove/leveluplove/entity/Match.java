package com.leveluplove.leveluplove.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user1; // Erster User im Match

    @ManyToOne
    private User user2; // Zweiter User im Match

    private LocalDateTime matchedAt;


}
