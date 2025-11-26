package com.shekhsite.backend.service;

import com.shekhsite.backend.DTO.DoodleDTO;

import java.util.List;

public interface IDoodleService {

    List<DoodleDTO> getAllDoodles();

    DoodleDTO getDoodleById(Long id);

    DoodleDTO createDoodle(DoodleDTO doodleDTO);

    DoodleDTO updateDoodle(Long id, DoodleDTO doodleDTO);

    void deleteDoodle(Long id);
}
