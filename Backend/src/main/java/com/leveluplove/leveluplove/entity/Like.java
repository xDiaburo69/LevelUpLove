package com.leveluplove.leveluplove.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primärschlüssel

    private Long likerId; // ID des Users, der geliket hat
    private Long likedId; // ID des Users, der geliket wurde

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; // Zeitstempel, wann der Like erstellt wurde

    public void setLiker(User liker) {
        this.likerId = liker.getId();
    }

    public void setLiked(User liked) {
        this.likedId = liked.getId();
    }
}
