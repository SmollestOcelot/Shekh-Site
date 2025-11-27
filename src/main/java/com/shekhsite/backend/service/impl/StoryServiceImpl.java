package com.shekhsite.backend.service.impl;


import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.common.exception.NotFoundException;
import com.shekhsite.backend.common.exception.ResourceNotFoundException;
import com.shekhsite.backend.service.IStoryService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StoryServiceImpl implements IStoryService {

    private final Map<Long, StoryDTO> stories = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<StoryDTO> getAllStories() {
        return new ArrayList<>(stories.values());
    }

    private StoryDTO requireStory(Long id) {
        return Optional.ofNullable(stories.get(id))
                .orElseThrow(() -> new NotFoundException("Story with id " + id + " not found"));
    }

    @Override
    public StoryDTO getStoryById(Long id) {
        return requireStory(id);
    }


    @Override
    public StoryDTO createStory(StoryDTO storyDTO) {
        long id = idGenerator.getAndIncrement();
        storyDTO.setId(id);
        stories.put(id, storyDTO);
        return storyDTO;
    }

    @Override
    public StoryDTO updateStory(Long id, StoryDTO storyDTO) {
        if (!stories.containsKey(id)) {
            throw new NoSuchElementException("Story not found with id " + id);
        }
        storyDTO.setId(id);
        stories.put(id, storyDTO);
        return storyDTO;
    }

    @Override
    public void deleteStory(Long id) {
        stories.remove(id);
    }
}
