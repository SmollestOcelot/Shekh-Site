package com.shekhsite.backend.service.impl;

import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.common.exception.ResourceNotFoundException;
import com.shekhsite.backend.model.Story;
import com.shekhsite.backend.repository.StoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoryServiceImplTest {

    @Mock
    private StoryRepository storyRepository;

    @InjectMocks
    private StoryServiceImpl storyService;

    private Story story;
    private StoryDTO storyDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        story = new Story();
        story.setTitle("Test Story");
        story.setBody("This is a test story");
        story.setTags("tag1,tag2");
        story.setCategory("fiction");
        story.setAuthor("Shekh");
        // Use reflection to set the timestamps since @PrePersist won't run in unit tests
        try {
            var createdAtField = Story.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(story, now);

            var updatedAtField = Story.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(story, now);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        storyDTO = new StoryDTO();
        storyDTO.setTitle("Test Story");
        storyDTO.setBody("This is a test story");
        storyDTO.setTags("tag1,tag2");
        storyDTO.setCategory("fiction");
    }

    @Test
    void getAllStories_returnsAllStories() {
        // Given
        when(storyRepository.findAll()).thenReturn(List.of(story));

        // When
        List<StoryDTO> result = storyService.getAllStories();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Story", result.get(0).getTitle());
        verify(storyRepository).findAll();
    }

    @Test
    void getStoryById_existingId_returnsStory() {
        // Given
        when(storyRepository.findById(1L)).thenReturn(Optional.of(story));

        // When
        StoryDTO result = storyService.getStoryById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test Story", result.getTitle());
        assertEquals("This is a test story", result.getBody());
        verify(storyRepository).findById(1L);
    }

    @Test
    void getStoryById_nonExistingId_throwsException() {
        // Given
        when(storyRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> storyService.getStoryById(999L));
        verify(storyRepository).findById(999L);
    }

    @Test
    void createStory_savesStory() {
        // Given
        when(storyRepository.save(any(Story.class))).thenReturn(story);

        // When
        StoryDTO result = storyService.createStory(storyDTO);

        // Then
        assertNotNull(result);
        assertEquals("Test Story", result.getTitle());
        verify(storyRepository).save(any(Story.class));
    }

    @Test
    void updateStory_existingId_updatesStory() {
        // Given
        when(storyRepository.findById(1L)).thenReturn(Optional.of(story));
        when(storyRepository.save(any(Story.class))).thenReturn(story);

        StoryDTO updateDTO = new StoryDTO();
        updateDTO.setTitle("Updated Story");
        updateDTO.setBody("Updated body content");
        updateDTO.setTags("new,tags");
        updateDTO.setCategory("non-fiction");

        // When
        StoryDTO result = storyService.updateStory(1L, updateDTO);

        // Then
        assertNotNull(result);
        verify(storyRepository).findById(1L);
        verify(storyRepository).save(any(Story.class));
    }

    @Test
    void updateStory_nonExistingId_throwsException() {
        // Given
        when(storyRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> storyService.updateStory(999L, storyDTO));
        verify(storyRepository).findById(999L);
        verify(storyRepository, never()).save(any());
    }

    @Test
    void deleteStory_existingId_deletesSuccessfully() {
        // Given
        when(storyRepository.existsById(1L)).thenReturn(true);
        doNothing().when(storyRepository).deleteById(1L);

        // When
        storyService.deleteStory(1L);

        // Then
        verify(storyRepository).existsById(1L);
        verify(storyRepository).deleteById(1L);
    }

    @Test
    void deleteStory_nonExistingId_throwsException() {
        // Given
        when(storyRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> storyService.deleteStory(999L));
        verify(storyRepository).existsById(999L);
        verify(storyRepository, never()).deleteById(any());
    }
}