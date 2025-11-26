package com.shekhsite.backend.service.impl;

import com.shekhsite.backend.DTO.DoodleDTO;
import com.shekhsite.backend.service.IDoodleService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DoodleServiceImpl implements IDoodleService {

    private final Map<Long, DoodleDTO> doodles = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public List<DoodleDTO> getAllDoodles() {
        return new ArrayList<>(doodles.values());
    }

    @Override
    public DoodleDTO getDoodleById(Long id) {
        return doodles.get(id);
    }

    @Override
    public DoodleDTO createDoodle(DoodleDTO doodleDTO) {
        long id = idSequence.getAndIncrement();
        doodleDTO.setId(id);

        if (doodleDTO.getCreatedAt() == null) {
            doodleDTO.setCreatedAt(Instant.from(LocalDateTime.now()));
        }

        doodles.put(id, doodleDTO);
        return doodleDTO;
    }

    @Override
    public DoodleDTO updateDoodle(Long id, DoodleDTO doodleDTO) {
        DoodleDTO existing = doodles.get(id);
        if (existing == null) {
            return null;
        }

        doodleDTO.setId(id);
        if (doodleDTO.getCreatedAt() == null) {
            doodleDTO.setCreatedAt(existing.getCreatedAt());
        }

        doodles.put(id, doodleDTO);
        return doodleDTO;
    }

    @Override
    public void deleteDoodle(Long id) {
        doodles.remove(id);
    }
}

