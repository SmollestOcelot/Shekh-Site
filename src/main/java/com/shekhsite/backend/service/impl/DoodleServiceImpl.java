package com.shekhsite.backend.service.impl;

import com.shekhsite.backend.DTO.DoodleDTO;
import com.shekhsite.backend.common.exception.ResourceNotFoundException;
import com.shekhsite.backend.model.Doodle;
import com.shekhsite.backend.repository.DoodleRepository;
import com.shekhsite.backend.service.IDoodleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DoodleServiceImpl implements IDoodleService {

    private final DoodleRepository doodleRepository;

    public DoodleServiceImpl(DoodleRepository doodleRepository) {
        this.doodleRepository = doodleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoodleDTO> getAllDoodles() {
        return doodleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DoodleDTO getDoodleById(Long id) {
        Doodle doodle = doodleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doodle", id));
        return toDTO(doodle);
    }

    @Override
    public DoodleDTO createDoodle(DoodleDTO doodleDTO) {
        Doodle doodle = new Doodle();
        doodle.setTitle(doodleDTO.getTitle());
        doodle.setDescription(doodleDTO.getDescription());
        doodle.setImageUrl(doodleDTO.getImageUrl());
        doodle.setTags(doodleDTO.getTags());
        doodle.setAuthor("Shekh"); // Default author

        Doodle saved = doodleRepository.save(doodle);
        return toDTO(saved);
    }

    @Override
    public DoodleDTO updateDoodle(Long id, DoodleDTO doodleDTO) {
        Doodle existing = doodleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doodle", id));

        existing.setTitle(doodleDTO.getTitle());
        existing.setDescription(doodleDTO.getDescription());
        existing.setImageUrl(doodleDTO.getImageUrl());
        existing.setTags(doodleDTO.getTags());

        Doodle updated = doodleRepository.save(existing);
        return toDTO(updated);
    }

    @Override
    public void deleteDoodle(Long id) {
        if (!doodleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doodle", id);
        }
        doodleRepository.deleteById(id);
    }

    // Helper method to convert Entity to DTO
    private DoodleDTO toDTO(Doodle doodle) {
        return new DoodleDTO(
                doodle.getId(),
                doodle.getTitle(),
                doodle.getDescription(),
                doodle.getTags(),
                doodle.getImageUrl(),
                doodle.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant(),
                doodle.getUpdatedAt().atZone(ZoneId.systemDefault()).toInstant()
        );
    }
}