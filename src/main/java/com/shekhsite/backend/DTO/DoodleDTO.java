package com.shekhsite.backend.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public class DoodleDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be at most 200 characters")
    private String title;

    @Size(max = 300, message = "Description must be at most 300 characters")
    private String description;

    @Size(max = 200)
    private String tags;

    private String imageUrl;

    private Instant createdAt;

    private Instant updatedAt;

    public DoodleDTO() {
    }

    public DoodleDTO(Long id,
                     String title,
                     String description,
                     String tags,
                     String imageUrl,
                     Instant createdAt,
                     Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.imageUrl = imageUrl;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() { return tags; }

    public void setTags(String tags) { this.tags = tags;}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

