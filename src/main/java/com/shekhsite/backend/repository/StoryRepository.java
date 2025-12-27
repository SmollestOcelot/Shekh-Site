package com.shekhsite.backend.repository;

import com.shekhsite.backend.model.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    // Search by title (case-insensitive)
    List<Story> findByTitleContainingIgnoreCase(String title);

    // Filter by category
    List<Story> findByCategory(String category);

    // Filter by tags (partial match)
    List<Story> findByTagsContaining(String tag);

    // Search by author
    List<Story> findByAuthor(String author);

    // Combined search: title OR body contains keyword
    @Query("SELECT s FROM Story s WHERE " +
            "LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(s.body) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Story> searchByKeyword(@Param("keyword") String keyword);

    // Paginated version of findAll
    Page<Story> findAll(Pageable pageable);

    // Paginated search by category
    Page<Story> findByCategory(String category, Pageable pageable);

    // Find recent stories (useful for "latest posts" feature)
    List<Story> findTop5ByOrderByCreatedAtDesc();

    // Custom query: find stories by multiple tags (AND logic)
    @Query("SELECT s FROM Story s WHERE " +
            "s.tags LIKE CONCAT('%', :tag1, '%') AND " +
            "s.tags LIKE CONCAT('%', :tag2, '%')")
    List<Story> findByMultipleTags(@Param("tag1") String tag1,
                                   @Param("tag2") String tag2);
}