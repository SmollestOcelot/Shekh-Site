package com.shekhsite.backend.service.impl;

import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.common.exception.ResourceNotFoundException;
import com.shekhsite.backend.model.Story;
import com.shekhsite.backend.repository.StoryRepository;
import com.shekhsite.backend.service.IStoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StoryServiceImpl implements IStoryService {

    private final StoryRepository storyRepository;

    public StoryServiceImpl(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoryDTO> getAllStories() {
        return storyRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StoryDTO getStoryById(Long id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Story", id));
        return toDTO(story);
    }

    @Override
    public StoryDTO createStory(StoryDTO storyDTO) {
        Story story = new Story();
        story.setTitle(storyDTO.getTitle());
        story.setBody(storyDTO.getBody());
        story.setTags(storyDTO.getTags());
        story.setCategory(storyDTO.getCategory());
        story.setAuthor("Shekh"); // Default author

        Story saved = storyRepository.save(story);
        return toDTO(saved);
    }

    @Override
    public StoryDTO updateStory(Long id, StoryDTO storyDTO) {
        Story existing = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Story", id));

        existing.setTitle(storyDTO.getTitle());
        existing.setBody(storyDTO.getBody());
        existing.setTags(storyDTO.getTags());
        existing.setCategory(storyDTO.getCategory());

        Story updated = storyRepository.save(existing);
        return toDTO(updated);
    }

    @Override
    public void deleteStory(Long id) {
        if (!storyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Story", id);
        }
        storyRepository.deleteById(id);
    }

    // Enhanced search and filter operations
    @Override
    @Transactional(readOnly = true)
    public List<StoryDTO> searchByTitle(String title) {
        return storyRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoryDTO> searchByKeyword(String keyword) {
        return storyRepository.searchByKeyword(keyword)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoryDTO> filterByCategory(String category) {
        return storyRepository.findByCategory(category)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoryDTO> filterByTag(String tag) {
        return storyRepository.findByTagsContaining(tag)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoryDTO> getRecentStories() {
        return storyRepository.findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Pagination support
    @Override
    @Transactional(readOnly = true)
    public Page<StoryDTO> getAllStories(Pageable pageable) {
        return storyRepository.findAll(pageable)
                .map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StoryDTO> getStoriesByCategory(String category, Pageable pageable) {
        return storyRepository.findByCategory(category, pageable)
                .map(this::toDTO);
    }

    // Helper method to convert Entity to DTO
    private StoryDTO toDTO(Story story) {
        return new StoryDTO(
                story.getId(),
                story.getTitle(),
                story.getBody(),
                story.getTags(),
                story.getCategory(),
                story.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant(),
                story.getUpdatedAt().atZone(ZoneId.systemDefault()).toInstant()
        );
    }
}