package com.shekhsite.backend.controller;

import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.service.IStoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/stories")
@Validated
public class StoryController {

    private final IStoryService storyService;

    public StoryController(IStoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public ResponseEntity<List<StoryDTO>> getAllStories() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> getStoryById(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.getStoryById(id));
    }

    @PostMapping
    public ResponseEntity<StoryDTO> createStory(@Valid @RequestBody StoryDTO storyDTO) {
        StoryDTO created = storyService.createStory(storyDTO);
        // You can polish this later, but a basic Location is nice:
        URI location = URI.create("/api/stories/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoryDTO> updateStory(@PathVariable Long id,
                                                @Valid @RequestBody StoryDTO storyDTO) {
        StoryDTO updated = storyService.updateStory(id, storyDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }
}

