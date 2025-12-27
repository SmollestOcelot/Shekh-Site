package com.shekhsite.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shekhsite.backend.model.Doodle;

public interface DoodleRepository extends JpaRepository<Doodle, Long> {
}
