package com.shekhsite.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(nullable = false, length = 8000)
    private String body;

    @Column(length = 200)
    private String tags;

    @Column(length = 100)
    private String category;

    @Column(length = 100)
    private String author;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Story() {
        // required by JPA
    }

    public Story(String title, String body, String author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // getters & setters
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}