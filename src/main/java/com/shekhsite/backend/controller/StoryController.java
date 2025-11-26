package com.shekhsite.backend.controller;


import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.model.Story;
import com.shekhsite.backend.service.IStoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "http://localhost:4200") // for Angular later
public class StoryController {

    private final IStoryService istoryService;

    public StoryController(IStoryService istoryService) {
        this.istoryService = istoryService;
    }

    @GetMapping
    public List<StoryDTO> getAllStories() {
        return istoryService.getAllStories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> getStory(@PathVariable Long id) {
        StoryDTO story = istoryService.getStoryById(id);
        return story != null ? ResponseEntity.ok(story) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<StoryDTO> createStory(@RequestBody StoryDTO storyDTO) {
        StoryDTO created = istoryService.createStory(storyDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryDTO> updateStory(@PathVariable Long id,
                                                @RequestBody StoryDTO storyDTO) {
        StoryDTO updated = istoryService.updateStory(id, storyDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        istoryService.deleteStory(id);
        return ResponseEntity.noContent().build();

    }
}

