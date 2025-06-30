package com.techscout.newsfetcher.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "news_items")
public record NewsItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
    @Column(nullable = false, length = 500) String title,
    @Column(length = 2000) String description,
    @Column(name = "source_url", nullable = false, unique = true) String sourceUrl,
    @Column(name = "source_name", nullable = false) String sourceName,
    @Column(name = "publish_date", nullable = false) Instant publishDate
) {
    // Default constructor for JPA
    public NewsItem() {
        this(null, null, null, null, null, null);
    }
    
    // Constructor without id for creating new instances
    public NewsItem(String title, String description, String sourceUrl, String sourceName, Instant publishDate) {
        this(null, title, description, sourceUrl, sourceName, publishDate);
    }
}
