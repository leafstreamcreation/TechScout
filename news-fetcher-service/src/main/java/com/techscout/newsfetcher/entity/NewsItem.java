package com.techscout.newsfetcher.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "news_items")
public class NewsItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    @Column(nullable = false, length = 500) 
    private String title;
    @Column(length = 2000) 
    private String description;
    @Column(name = "source_url", nullable = false, unique = true)
    private String sourceUrl;
    @Column(name = "source_name", nullable = false)
    private String sourceName;
    @Column(name = "publish_date", nullable = false) 
    private Instant publishDate;
    
    // Default constructor for JPA
    protected NewsItem() {
    }
    
    // Constructor without id for creating new instances
    public NewsItem(String title, String description, String sourceUrl, String sourceName, Instant publishDate) {
        this.title = title;
        this.description = description;
        this.sourceUrl = sourceUrl;
        this.sourceName = sourceName;
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public Instant getPublishDate() {
        return publishDate;
    }
}
