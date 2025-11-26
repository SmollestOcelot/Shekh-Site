package com.shekhsite.backend.service;

import com.shekhsite.backend.DTO.StoryDTO;

import java.util.List;

public interface IStoryService {

    List<StoryDTO> getAllStories();

    StoryDTO getStoryById(Long id);

    StoryDTO createStory(StoryDTO storyDTO);

    StoryDTO updateStory(Long id, StoryDTO storyDTO);

    void deleteStory(Long id);
}
