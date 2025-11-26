package com.shekhsite.backend.controller;

import com.shekhsite.backend.DTO.DoodleDTO;
import com.shekhsite.backend.model.Doodle;
import com.shekhsite.backend.service.IDoodleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doodles")
@CrossOrigin(origins = "http://localhost:4200")
public class DoodleController {

    private final IDoodleService idoodleService;

    public DoodleController(IDoodleService idoodleService) {
        this.idoodleService = idoodleService;
    }

    @GetMapping
    public List<DoodleDTO> getAllDoodles() {
        return idoodleService.getAllDoodles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoodleDTO> getDoodle(@PathVariable Long id) {
        DoodleDTO doodle = idoodleService.getDoodleById(id);
        return doodle != null ? ResponseEntity.ok(doodle) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DoodleDTO> createDoodle(@RequestBody DoodleDTO doodleDTO) {
        DoodleDTO created = idoodleService.createDoodle(doodleDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoodleDTO> updateDoodle(@PathVariable Long id,
                                                  @RequestBody DoodleDTO doodleDTO) {
        DoodleDTO updated = idoodleService.updateDoodle(id, doodleDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoodle(@PathVariable Long id) {
        idoodleService.deleteDoodle(id);
        return ResponseEntity.noContent().build();
    }
}

