package com.shekhsite.backend.service;

import com.shekhsite.backend.DTO.StoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStoryService {

    // Basic CRUD operations
    List<StoryDTO> getAllStories();

    StoryDTO getStoryById(Long id);

    StoryDTO createStory(StoryDTO storyDTO);

    StoryDTO updateStory(Long id, StoryDTO storyDTO);

    void deleteStory(Long id);

    // Enhanced search and filter operations
    List<StoryDTO> searchByTitle(String title);

    List<StoryDTO> searchByKeyword(String keyword);

    List<StoryDTO> filterByCategory(String category);

    List<StoryDTO> filterByTag(String tag);

    List<StoryDTO> getRecentStories();

    // Pagination support
    Page<StoryDTO> getAllStories(Pageable pageable);

    Page<StoryDTO> getStoriesByCategory(String category, Pageable pageable);
}