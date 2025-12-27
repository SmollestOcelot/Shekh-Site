package com.shekhsite.backend.config;

import com.shekhsite.backend.model.Doodle;
import com.shekhsite.backend.model.Story;
import com.shekhsite.backend.repository.DoodleRepository;
import com.shekhsite.backend.repository.StoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initializes the database with sample data on startup
 * Comment out @Component to disable sample data
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final DoodleRepository doodleRepository;
    private final StoryRepository storyRepository;

    public DataInitializer(DoodleRepository doodleRepository,
                           StoryRepository storyRepository) {
        this.doodleRepository = doodleRepository;
        this.storyRepository = storyRepository;
    }

    @Override
    public void run(String... args) {
        // Only initialize if database is empty
        if (doodleRepository.count() == 0) {
            initializeDoodles();
        }

        if (storyRepository.count() == 0) {
            initializeStories();
        }
    }

    private void initializeDoodles() {
        Doodle doodle1 = new Doodle();
        doodle1.setTitle("Cat in Space");
        doodle1.setDescription("A doodle of a little cat drifting among stars.");
        doodle1.setTags("cats,space,cute");
        doodle1.setImageUrl("https://picsum.photos/400/300?random=1");
        doodle1.setAuthor("Shekh");

        Doodle doodle2 = new Doodle();
        doodle2.setTitle("Coffee and Comet");
        doodle2.setDescription("Sketch of a mug of coffee next to a shooting star.");
        doodle2.setTags("coffee,space,cozy");
        doodle2.setImageUrl("https://picsum.photos/400/300?random=2");
        doodle2.setAuthor("Shekh");

        Doodle doodle3 = new Doodle();
        doodle3.setTitle("Dreaming Owl");
        doodle3.setDescription("An owl perched on a crescent moon, lost in thought.");
        doodle3.setTags("owl,moon,dreamy");
        doodle3.setImageUrl("https://picsum.photos/400/300?random=3");
        doodle3.setAuthor("Shekh");

        doodleRepository.save(doodle1);
        doodleRepository.save(doodle2);
        doodleRepository.save(doodle3);

        System.out.println("✓ Initialized sample doodles");
    }

    private void initializeStories() {
        Story story1 = new Story();
        story1.setTitle("The Cat That Drew the Moon");
        story1.setBody("Once upon a time, there lived an artist cat who spent every night drawing constellations for their friends. Each star was a memory, each constellation a story waiting to be told...");
        story1.setTags("fantasy,cats,wholesome");
        story1.setCategory("fiction");
        story1.setAuthor("Shekh");

        Story story2 = new Story();
        story2.setTitle("Rainy Day Notebook");
        story2.setBody("The sound of rain against the window creates the perfect backdrop for creativity. With a warm cup of tea and a blank page, the afternoon stretches out with endless possibilities...");
        story2.setTags("slice-of-life,cozy");
        story2.setCategory("personal");
        story2.setAuthor("Shekh");

        Story story3 = new Story();
        story3.setTitle("Learning to Code");
        story3.setBody("Every developer remembers their first 'Hello World'. It's not just about the syntax or the output—it's about realizing that you can create something from nothing, that you can tell a machine what to do and watch it come to life...");
        story3.setTags("coding,journey,tech");
        story3.setCategory("personal");
        story3.setAuthor("Shekh");

        storyRepository.save(story1);
        storyRepository.save(story2);
        storyRepository.save(story3);

        System.out.println("✓ Initialized sample stories");
    }
}