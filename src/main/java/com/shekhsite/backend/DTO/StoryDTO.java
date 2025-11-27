package com.shekhsite.backend.DTO;

import java.time.Instant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StoryDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be at most 200 characters")
    private String title;

    @NotBlank(message = "Content is required")
    private String body;

    @Size(max = 100, message = "Category must be at most 100 characters")
    private String category;

    private Instant createdAt;

    private Instant updatedAt;

    public StoryDTO() {
    }

    public StoryDTO(Long id,
                    String title,
                    String body,
                    String category,
                    Instant createdAt,
                    Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
