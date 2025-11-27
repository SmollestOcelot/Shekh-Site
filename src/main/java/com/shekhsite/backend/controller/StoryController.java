package com.shekhsite.backend.controller;


import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.model.Story;
import com.shekhsite.backend.service.IStoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final IStoryService storyService;

    public StoryController(IStoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public List<StoryDTO> getAllStories() {
        return storyService.getAllStories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> getStoryById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(storyService.getStoryById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<StoryDTO> createStory(@Valid @RequestBody StoryDTO storyDTO) {
        StoryDTO created = storyService.createStory(storyDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryDTO> updateStory(@PathVariable Long id,
                                                @RequestBody StoryDTO storyDTO) {
        try {
            return ResponseEntity.ok(storyService.updateStory(id, storyDTO));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }
}

