package com.shekhsite.backend.controller;

import com.shekhsite.backend.DTO.DoodleDTO;
import com.shekhsite.backend.service.IDoodleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/doodles")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class DoodleController {

    private final IDoodleService doodleService;

    public DoodleController(IDoodleService doodleService) {
        this.doodleService = doodleService;
    }

    // Basic CRUD endpoints
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

    // Enhanced search and filter endpoints
    @GetMapping("/search")
    public ResponseEntity<List<DoodleDTO>> searchDoodles(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String keyword) {

        if (title != null && !title.isBlank()) {
            return ResponseEntity.ok(doodleService.searchByTitle(title));
        } else if (keyword != null && !keyword.isBlank()) {
            return ResponseEntity.ok(doodleService.searchByKeyword(keyword));
        }

        return ResponseEntity.ok(doodleService.getAllDoodles());
    }

    @GetMapping("/filter/tag/{tag}")
    public ResponseEntity<List<DoodleDTO>> filterByTag(@PathVariable String tag) {
        return ResponseEntity.ok(doodleService.filterByTag(tag));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<DoodleDTO>> getRecentDoodles() {
        return ResponseEntity.ok(doodleService.getRecentDoodles());
    }

    // Paginated endpoints
    @GetMapping("/paginated")
    public ResponseEntity<Page<DoodleDTO>> getDoodlesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return ResponseEntity.ok(doodleService.getAllDoodles(pageable));
    }
}