package com.shekhsite.backend.service.impl;

import com.shekhsite.backend.DTO.DoodleDTO;
import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.common.exception.NotFoundException;
import com.shekhsite.backend.common.exception.ResourceNotFoundException;
import com.shekhsite.backend.service.IDoodleService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
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
        doodles.put(id, doodleDTO);
        return doodleDTO;
    }

    @Override
    public DoodleDTO updateDoodle(Long id, DoodleDTO doodleDTO) {
        if (!doodles.containsKey(id)) {
            throw new NoSuchElementException("Doodle not found with id " + id);
        }
        doodleDTO.setId(id);
        doodles.put(id, doodleDTO);
        return doodleDTO;
    }

    @Override
    public void deleteDoodle(Long id) {
        doodles.remove(id);
    }
}