package com.shekhsite.backend.controller;

import com.shekhsite.backend.DTO.DoodleDTO;
import com.shekhsite.backend.model.Doodle;
import com.shekhsite.backend.service.IDoodleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doodles")
public class DoodleController {

    private final IDoodleService doodleService;

    public DoodleController(IDoodleService doodleService) {
        this.doodleService = doodleService;
    }

    @GetMapping
    public List<DoodleDTO> getAllDoodles() {
        return doodleService.getAllDoodles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoodleDTO> getDoodleById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(doodleService.getDoodleById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DoodleDTO> createDoodle(@Valid @RequestBody DoodleDTO doodleDTO) {
        DoodleDTO created = doodleService.createDoodle(doodleDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoodleDTO> updateDoodle(@PathVariable Long id,
                                                  @RequestBody DoodleDTO doodleDTO) {
        try {
            return ResponseEntity.ok(doodleService.updateDoodle(id, doodleDTO));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoodle(@PathVariable Long id) {
        doodleService.deleteDoodle(id);
        return ResponseEntity.noContent().build();
    }
}