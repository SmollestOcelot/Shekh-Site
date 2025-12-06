package com.shekhsite.backend.service.impl;

import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.common.exception.NotFoundException;
import com.shekhsite.backend.service.IStoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

        Instant now = Instant.now();
        if (storyDTO.getCreatedAt() == null) {
            storyDTO.setCreatedAt(now);
        }
        storyDTO.setUpdatedAt(now);

        stories.put(id, storyDTO);
        return storyDTO;
    }

    @Override
    public StoryDTO updateStory(Long id, StoryDTO storyDTO) {
        StoryDTO existing = requireStory(id); // throws NotFoundException if missing

        existing.setTitle(storyDTO.getTitle());
        existing.setBody(storyDTO.getBody());
        existing.setCategory(storyDTO.getCategory());
        existing.setUpdatedAt(Instant.now());

        stories.put(id, existing);
        return existing;
    }

    @Override
    public void deleteStory(Long id) {
        // will throw if it doesn't exist
        requireStory(id);
        stories.remove(id);
    }

    @PostConstruct
    public void initSampleStories() {
        createStory(new StoryDTO(
                null,
                "The Cat That Drew the Moon",
                "A short story about an artist cat who draws constellations for their friends.",
                "fantasy, cats",
                "fiction",
                null,
                null
        ));

        createStory(new StoryDTO(
                null,
                "Rainy Day Notebook",
                "Slice-of-life notes from a cozy afternoon sketching and writing.",
                "slice_of_life",
                "fiction",
                null,
                null
        ));
    }
}
