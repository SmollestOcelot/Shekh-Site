package com.shekhsite.backend.service.impl;

import com.shekhsite.backend.DTO.DoodleDTO;
import com.shekhsite.backend.common.exception.ResourceNotFoundException;
import com.shekhsite.backend.model.Doodle;
import com.shekhsite.backend.repository.DoodleRepository;
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
class DoodleServiceImplTest {

    @Mock
    private DoodleRepository doodleRepository;

    @InjectMocks
    private DoodleServiceImpl doodleService;

    private Doodle doodle;
    private DoodleDTO doodleDTO;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        doodle = new Doodle();
        doodle.setTitle("Test Doodle");
        doodle.setDescription("A test doodle");
        doodle.setImageUrl("https://example.com/image.png");
        doodle.setTags("tag1,tag2");
        doodle.setAuthor("Shekh");
        // Use reflection to set the timestamps since @PrePersist won't run in unit tests
        try {
            var createdAtField = Doodle.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(doodle, now);

            var updatedAtField = Doodle.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(doodle, now);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        doodleDTO = new DoodleDTO();
        doodleDTO.setTitle("Test Doodle");
        doodleDTO.setDescription("A test doodle");
        doodleDTO.setImageUrl("https://example.com/image.png");
        doodleDTO.setTags("tag1,tag2");
    }

    @Test
    void getAllDoodles_returnsAllDoodles() {
        // Given
        when(doodleRepository.findAll()).thenReturn(List.of(doodle));

        // When
        List<DoodleDTO> result = doodleService.getAllDoodles();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Doodle", result.get(0).getTitle());
        verify(doodleRepository).findAll();
    }

    @Test
    void getDoodleById_existingId_returnsDoodle() {
        // Given
        when(doodleRepository.findById(1L)).thenReturn(Optional.of(doodle));

        // When
        DoodleDTO result = doodleService.getDoodleById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test Doodle", result.getTitle());
        verify(doodleRepository).findById(1L);
    }

    @Test
    void getDoodleById_nonExistingId_throwsException() {
        // Given
        when(doodleRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> doodleService.getDoodleById(999L));
        verify(doodleRepository).findById(999L);
    }

    @Test
    void createDoodle_savesDoodle() {
        // Given
        when(doodleRepository.save(any(Doodle.class))).thenReturn(doodle);

        // When
        DoodleDTO result = doodleService.createDoodle(doodleDTO);

        // Then
        assertNotNull(result);
        assertEquals("Test Doodle", result.getTitle());
        verify(doodleRepository).save(any(Doodle.class));
    }

    @Test
    void updateDoodle_existingId_updatesDoodle() {
        // Given
        when(doodleRepository.findById(1L)).thenReturn(Optional.of(doodle));
        when(doodleRepository.save(any(Doodle.class))).thenReturn(doodle);

        DoodleDTO updateDTO = new DoodleDTO();
        updateDTO.setTitle("Updated Doodle");
        updateDTO.setDescription("Updated description");
        updateDTO.setImageUrl("https://example.com/new.png");
        updateDTO.setTags("new,tags");

        // When
        DoodleDTO result = doodleService.updateDoodle(1L, updateDTO);

        // Then
        assertNotNull(result);
        verify(doodleRepository).findById(1L);
        verify(doodleRepository).save(any(Doodle.class));
    }

    @Test
    void updateDoodle_nonExistingId_throwsException() {
        // Given
        when(doodleRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> doodleService.updateDoodle(999L, doodleDTO));
        verify(doodleRepository).findById(999L);
        verify(doodleRepository, never()).save(any());
    }

    @Test
    void deleteDoodle_existingId_deletesSuccessfully() {
        // Given
        when(doodleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(doodleRepository).deleteById(1L);

        // When
        doodleService.deleteDoodle(1L);

        // Then
        verify(doodleRepository).existsById(1L);
        verify(doodleRepository).deleteById(1L);
    }

    @Test
    void deleteDoodle_nonExistingId_throwsException() {
        // Given
        when(doodleRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class,
                () -> doodleService.deleteDoodle(999L));
        verify(doodleRepository).existsById(999L);
        verify(doodleRepository, never()).deleteById(any());
    }
}