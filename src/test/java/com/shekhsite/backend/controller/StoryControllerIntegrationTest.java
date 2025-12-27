package com.shekhsite.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shekhsite.backend.DTO.StoryDTO;
import com.shekhsite.backend.model.Story;
import com.shekhsite.backend.repository.StoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Story testStory;

    @BeforeEach
    void setUp() {
        storyRepository.deleteAll();

        testStory = new Story();
        testStory.setTitle("Integration Test Story");
        testStory.setBody("This is a test story for integration testing");
        testStory.setTags("test,integration");
        testStory.setCategory("testing");
        testStory.setAuthor("TestAuthor");
        testStory = storyRepository.save(testStory);
    }

    @Test
    void getAllStories_returnsStories() throws Exception {
        mockMvc.perform(get("/api/stories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].title", is("Integration Test Story")));
    }

    @Test
    void getStoryById_existingId_returnsStory() throws Exception {
        mockMvc.perform(get("/api/stories/{id}", testStory.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Integration Test Story")))
                .andExpect(jsonPath("$.body", is("This is a test story for integration testing")))
                .andExpect(jsonPath("$.category", is("testing")));
    }

    @Test
    void getStoryById_nonExistingId_returns404() throws Exception {
        mockMvc.perform(get("/api/stories/{id}", 9999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")));
    }

    @Test
    void createStory_validData_createsStory() throws Exception {
        StoryDTO newStory = new StoryDTO();
        newStory.setTitle("New Story");
        newStory.setBody("New story content");
        newStory.setTags("new,story");
        newStory.setCategory("fiction");

        mockMvc.perform(post("/api/stories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStory)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.title", is("New Story")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void createStory_invalidData_returns400() throws Exception {
        StoryDTO invalidStory = new StoryDTO();
        // Missing required fields

        mockMvc.perform(post("/api/stories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidStory)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    void updateStory_existingId_updatesStory() throws Exception {
        StoryDTO updateData = new StoryDTO();
        updateData.setTitle("Updated Title");
        updateData.setBody("Updated body");
        updateData.setTags("updated");
        updateData.setCategory("updated-category");

        mockMvc.perform(put("/api/stories/{id}", testStory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.body", is("Updated body")));
    }

    @Test
    void deleteStory_existingId_deletesSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/stories/{id}", testStory.getId()))
                .andExpect(status().isNoContent());

        // Verify it was deleted
        mockMvc.perform(get("/api/stories/{id}", testStory.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void searchStories_byTitle_returnsMatchingStories() throws Exception {
        mockMvc.perform(get("/api/stories/search")
                        .param("title", "Integration"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].title", containsString("Integration")));
    }

    @Test
    void filterByCategory_returnsMatchingStories() throws Exception {
        mockMvc.perform(get("/api/stories/filter/category/{category}", "testing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].category", is("testing")));
    }

    @Test
    void getRecentStories_returnsStories() throws Exception {
        mockMvc.perform(get("/api/stories/recent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void getPaginatedStories_returnsPage() throws Exception {
        mockMvc.perform(get("/api/stories/paginated")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", isA(java.util.List.class)))
                .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(5)));
    }
}