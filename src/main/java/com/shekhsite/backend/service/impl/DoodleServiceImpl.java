package com.shekhsite.backend.service.impl;

import com.shekhsite.backend.DTO.DoodleDTO;
import com.shekhsite.backend.common.exception.NotFoundException;
import com.shekhsite.backend.service.IDoodleService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DoodleServiceImpl implements IDoodleService {

    private final Map<Long, DoodleDTO> doodles = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<DoodleDTO> getAllDoodles() {
        return new ArrayList<>(doodles.values());
    }

    private DoodleDTO requireDoodle(Long id) {
        return Optional.ofNullable(doodles.get(id))
                .orElseThrow(() -> new NotFoundException("Doodle with id " + id + " not found"));
    }

    @Override
    public DoodleDTO getDoodleById(Long id) {
        return requireDoodle(id);
    }

    @Override
    public DoodleDTO createDoodle(DoodleDTO doodleDTO) {
        long id = idGenerator.getAndIncrement();
        doodleDTO.setId(id);

        Instant now = Instant.now();
        if (doodleDTO.getCreatedAt() == null) {
            doodleDTO.setCreatedAt(now);
        }
        doodleDTO.setUpdatedAt(now);

        doodles.put(id, doodleDTO);
        return doodleDTO;
    }

    @Override
    public DoodleDTO updateDoodle(Long id, DoodleDTO doodleDTO) {
        DoodleDTO existing = requireDoodle(id);

        existing.setTitle(doodleDTO.getTitle());
        existing.setDescription(doodleDTO.getDescription());
        existing.setImageUrl(doodleDTO.getImageUrl());
        existing.setUpdatedAt(Instant.now());

        doodles.put(id, existing);
        return existing;
    }

    @Override
    public void deleteDoodle(Long id) {
        requireDoodle(id);
        doodles.remove(id);
    }

    @PostConstruct
    public void initSampleDoodles() {
        createDoodle(new DoodleDTO(
                null,
                "Cat in Space",
                "A doodle of a little cat drifting among stars.",
                "cats, space",
                "https://example.com/images/cat-in-space.png",
                null,
                null
        ));

        createDoodle(new DoodleDTO(
                null,
                "Coffee and Comet",
                "Sketch of a mug of coffee next to a shooting star.",
                "coffee, stuff",
                "https://example.com/images/coffee-comet.png",
                null,
                null
        ));
    }
}

