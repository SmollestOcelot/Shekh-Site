package com.shekhsite.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shekhsite.backend.model.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
