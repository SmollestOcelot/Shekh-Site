package com.shekhsite.backend.service.impl;


import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.service.IStoryService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StoryServiceImpl implements IStoryService {

    private final Map<Long, StoryDTO> stories = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public List<StoryDTO> getAllStories() {
        return new ArrayList<>(stories.values());
    }

    @Override
    public StoryDTO getStoryById(Long id) {
        return stories.get(id);
    }

    @Override
    public StoryDTO createStory(StoryDTO storyDTO) {
        long id = idSequence.getAndIncrement();
        storyDTO.setId(id);

        if (storyDTO.getCreatedAt() == null) {
            storyDTO.setCreatedAt(Instant.from(LocalDateTime.now()));
        }

        stories.put(id, storyDTO);
        return storyDTO;
    }

    @Override
    public StoryDTO updateStory(Long id, StoryDTO storyDTO) {
        StoryDTO existing = stories.get(id);
        if (existing == null) {
            return null;
        }

        storyDTO.setId(id);
        if (storyDTO.getCreatedAt() == null) {
            storyDTO.setCreatedAt(existing.getCreatedAt());
        }

        stories.put(id, storyDTO);
        return storyDTO;
    }

    @Override
    public void deleteStory(Long id) {
        stories.remove(id);
    }
}
