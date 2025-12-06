package com.shekhsite.backend.service.impl;

import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StoryServiceImplTest {

    private StoryServiceImpl storyService;

    @BeforeEach
    void setUp() {
        storyService = new StoryServiceImpl();
    }

    @Test
    void createStory_assignsIdAndStores() {
        StoryDTO dto = new StoryDTO(
                null,
                "Test Title",
                "Body text",
                "tag1,tag2",
                "Story",
                Instant.now(),
                Instant.now()
        );

        StoryDTO created = storyService.createStory(dto);

        assertNotNull(created.getId());
        List<StoryDTO> all = storyService.getAllStories();
        assertEquals(1, all.size());
        assertEquals("Test Title", all.get(0).getTitle());
    }

    @Test
    void getStoryById_returnsExisting() {
        StoryDTO dto = new StoryDTO(
                null,
                "Another",
                "Body",
                null,
                null,
                Instant.now(),
                Instant.now()
        );
        StoryDTO created = storyService.createStory(dto);

        StoryDTO found = storyService.getStoryById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("Another", found.getTitle());
    }

    @Test
    void getStoryById_throwsNotFoundForMissing() {
        assertThrows(NotFoundException.class, () -> storyService.getStoryById(999L));
    }

    @Test
    void updateStory_updatesExisting() {
        StoryDTO dto = new StoryDTO(
                null,
                "Original",
                "Body",
                null,
                null,
                Instant.now(),
                Instant.now()
        );
        StoryDTO created = storyService.createStory(dto);

        StoryDTO update = new StoryDTO(
                null,
                "Updated",
                "New Body",
                "tag",
                "Story",
                created.getCreatedAt(),
                Instant.now()
        );

        StoryDTO updated = storyService.updateStory(created.getId(), update);

        assertEquals("Updated", updated.getTitle());
        assertEquals("New Body", updated.getBody());
    }

    @Test
    void updateStory_nonExisting_throwsNotFound() {
        StoryDTO update = new StoryDTO(
                null,
                "Updated",
                "Body",
                null,
                null,
                Instant.now(),
                Instant.now()
        );

        assertThrows(NotFoundException.class,
                () -> storyService.updateStory(123L, update));
    }

    @Test
    void deleteStory_removesIt() {
        StoryDTO dto = new StoryDTO(
                null,
                "To Delete",
                "Body",
                null,
                null,
                Instant.now(),
                Instant.now()
        );
        StoryDTO created = storyService.createStory(dto);

        storyService.deleteStory(created.getId());

        assertThrows(NotFoundException.class,
                () -> storyService.getStoryById(created.getId()));
    }
}

