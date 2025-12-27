package com.shekhsite.backend.service;

import com.shekhsite.backend.DTO.DoodleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDoodleService {

    // Basic CRUD operations
    List<DoodleDTO> getAllDoodles();

    DoodleDTO getDoodleById(Long id);

    DoodleDTO createDoodle(DoodleDTO doodleDTO);

    DoodleDTO updateDoodle(Long id, DoodleDTO doodleDTO);

    void deleteDoodle(Long id);

    // Enhanced search and filter operations
    List<DoodleDTO> searchByTitle(String title);

    List<DoodleDTO> searchByKeyword(String keyword);

    List<DoodleDTO> filterByTag(String tag);

    List<DoodleDTO> getRecentDoodles();

    // Pagination support
    Page<DoodleDTO> getAllDoodles(Pageable pageable);
}