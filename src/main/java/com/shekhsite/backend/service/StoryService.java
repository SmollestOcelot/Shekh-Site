package com.shekhsite.backend.service;


import com.shekhsite.backend.model.Story;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StoryService {

    private final Map<Long, Story> stories = new LinkedHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Story> findAll() {
        return new ArrayList<>(stories.values());
    }

    public Optional<Story> findById(Long id) {
        return Optional.ofNullable(stories.get(id));
    }

    public Story create(String title, String content, String author) {
        Long id = idGenerator.getAndIncrement();
        if (author == null || author.isBlank()) {
            author = "Shekh";
        }
        Story story = new Story(id, title, content, author, LocalDateTime.now());
        stories.put(id, story);
        return story;
    }

    public boolean delete(Long id) {
        return stories.remove(id) != null;
    }
}

