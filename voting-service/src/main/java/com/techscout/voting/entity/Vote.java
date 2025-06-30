package com.techscout.voting.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public record Vote(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
    @Column(name = "news_item_id", nullable = false) Long newsItemId,
    @Column(nullable = false) int value,
    @Column(name = "user_id", nullable = false) String userId
) {
    // Default constructor for JPA
    public Vote() {
        this(null, null, 0, null);
    }
    
    // Constructor without id for creating new instances
    public Vote(Long newsItemId, int value, String userId) {
        this(null, newsItemId, value, userId);
    }
    
    // Validation method
    public boolean isValidVote() {
        return value == 1 || value == -1;
    }
}
