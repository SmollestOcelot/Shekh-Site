package com.shekhsite.backend.controller;

import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.service.IStoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/stories")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class StoryController {

    private final IStoryService storyService;

    public StoryController(IStoryService storyService) {
        this.storyService = storyService;
    }

    // Basic CRUD endpoints
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

    // Enhanced search and filter endpoints
    @GetMapping("/search")
    public ResponseEntity<List<StoryDTO>> searchStories(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String keyword) {

        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(storyService.searchByTitle(title));
        } else if (keyword != null && !keyword.isBlank()) {
            return ResponseEntity.ok(storyService.searchByKeyword(keyword));
        }

        return ResponseEntity.ok(storyService.getAllStories());
    }

    @GetMapping("/filter/category/{category}")
    public ResponseEntity<List<StoryDTO>> filterByCategory(@PathVariable String category) {
        return ResponseEntity.ok(storyService.filterByCategory(category));
    }

    @GetMapping("/filter/tag/{tag}")
    public ResponseEntity<List<StoryDTO>> filterByTag(@PathVariable String tag) {
        return ResponseEntity.ok(storyService.filterByTag(tag));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<StoryDTO>> getRecentStories() {
        return ResponseEntity.ok(storyService.getRecentStories());
    }

    // Paginated endpoints
    @GetMapping("/paginated")
    public ResponseEntity<Page<StoryDTO>> getStoriesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ResponseEntity.ok(storyService.getAllStories(pageable));
    }

    @GetMapping("/paginated/category/{category}")
    public ResponseEntity<Page<StoryDTO>> getStoriesByCategoryPaginated(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ResponseEntity.ok(storyService.getStoriesByCategory(category, pageable));
    }
}