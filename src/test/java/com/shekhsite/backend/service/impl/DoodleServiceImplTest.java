package com.shekhsite.backend.service.impl;

import com.shekhsite.backend.DTO.DoodleDTO;
import com.shekhsite.backend.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoodleServiceImplTest {

    private DoodleServiceImpl doodleService;

    @BeforeEach
    void setUp() {
        doodleService = new DoodleServiceImpl();
    }

    @Test
    void createDoodle_assignsIdAndStores() {
        DoodleDTO dto = new DoodleDTO(
                null,
                "Doodle 1",
                "A cute doodle",
                "tag1,tag2",
                "https://example.com/image.png",
                Instant.now(),
                Instant.now()
        );

        DoodleDTO created = doodleService.createDoodle(dto);

        assertNotNull(created.getId());
        List<DoodleDTO> all = doodleService.getAllDoodles();
        assertEquals(1, all.size());
        assertEquals("Doodle 1", all.get(0).getTitle());
    }

    @Test
    void getDoodleById_throwsNotFoundForMissing() {
        assertThrows(NotFoundException.class,
                () -> doodleService.getDoodleById(999L));
    }
}

