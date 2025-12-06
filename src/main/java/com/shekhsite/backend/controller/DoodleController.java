package com.shekhsite.backend.controller;

import com.shekhsite.backend.DTO.DoodleDTO;
import com.shekhsite.backend.service.IDoodleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/doodles")
@Validated
public class DoodleController {

    private final IDoodleService doodleService;

    public DoodleController(IDoodleService doodleService) {
        this.doodleService = doodleService;
    }

    @GetMapping
    public ResponseEntity<List<DoodleDTO>> getAllDoodles() {
        return ResponseEntity.ok(doodleService.getAllDoodles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoodleDTO> getDoodleById(@PathVariable Long id) {
        return ResponseEntity.ok(doodleService.getDoodleById(id));
    }

    @PostMapping
    public ResponseEntity<DoodleDTO> createDoodle(@Valid @RequestBody DoodleDTO doodleDTO) {
        DoodleDTO created = doodleService.createDoodle(doodleDTO);
        URI location = URI.create("/api/doodles/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoodleDTO> updateDoodle(@PathVariable Long id,
                                                  @Valid @RequestBody DoodleDTO doodleDTO) {
        DoodleDTO updated = doodleService.updateDoodle(id, doodleDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoodle(@PathVariable Long id) {
        doodleService.deleteDoodle(id);
        return ResponseEntity.noContent().build();
    }
}
