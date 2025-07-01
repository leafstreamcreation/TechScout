package com.techscout.voting.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    @Column(name = "news_item_id", nullable = false) 
    private Long newsItemId;
    @Column(nullable = false) 
    private int value;
    @Column(name = "user_id", nullable = false) 
    private String userId;
    // Default constructor for JPA
    protected Vote() {
    }
    
    // Constructor without id for creating new instances
    public Vote(Long newsItemId, int value, String userId) {
        this.newsItemId = newsItemId;
        this.value = value;
        this.userId = userId;
    }

    // Constructor with id for updating existing votes
    public Vote(Long id, Long newsItemId, int value, String userId) {
        this.id = id;
        this.newsItemId = newsItemId;
        this.value = value;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    // Validation method
    public boolean isValidVote() {
        return value == 1 || value == -1;
    }
}
