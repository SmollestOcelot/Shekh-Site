package com.shekhsite.backend.service;

import com.shekhsite.backend.model.Doodle;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DoodleService {

    private final Map<Long, Doodle> doodles = new LinkedHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Doodle> findAll() {
        return new ArrayList<>(doodles.values());
    }

    public Optional<Doodle> findById(Long id) {
        return Optional.ofNullable(doodles.get(id));
    }

    public Doodle create(String title, String description, String imageUrl, String author) {
        Long id = idGenerator.getAndIncrement();
        if (author == null || author.isBlank()) {
            author = "Shekh";
        }
        Doodle doodle = new Doodle(id, title, description, imageUrl, author, LocalDateTime.now());
        doodles.put(id, doodle);
        return doodle;
    }

    public boolean delete(Long id) {
        return doodles.remove(id) != null;
    }
}

