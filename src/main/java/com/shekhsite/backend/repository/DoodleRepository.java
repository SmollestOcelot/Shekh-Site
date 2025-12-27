package com.shekhsite.backend.repository;

import com.shekhsite.backend.model.Doodle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoodleRepository extends JpaRepository<Doodle, Long> {

    // Search by title (case-insensitive)
    List<Doodle> findByTitleContainingIgnoreCase(String title);

    // Filter by tags (partial match)
    List<Doodle> findByTagsContaining(String tag);

    // Search by author
    List<Doodle> findByAuthor(String author);

    // Combined search: title OR description contains keyword
    @Query("SELECT d FROM Doodle d WHERE " +
            "LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Doodle> searchByKeyword(@Param("keyword") String keyword);

    // Paginated version of findAll
    Page<Doodle> findAll(Pageable pageable);

    // Find recent doodles (useful for "latest artwork" feature)
    List<Doodle> findTop6ByOrderByCreatedAtDesc();

    // Custom query: find doodles by multiple tags (AND logic)
    @Query("SELECT d FROM Doodle d WHERE " +
            "d.tags LIKE CONCAT('%', :tag1, '%') AND " +
            "d.tags LIKE CONCAT('%', :tag2, '%')")
    List<Doodle> findByMultipleTags(@Param("tag1") String tag1,
                                    @Param("tag2") String tag2);
}