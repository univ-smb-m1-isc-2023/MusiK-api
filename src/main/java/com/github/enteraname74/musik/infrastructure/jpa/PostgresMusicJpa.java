package com.github.enteraname74.musik.infrastructure.jpa;


import com.github.enteraname74.musik.infrastructure.model.PostgresMusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that will provide methods for managing PostgresMusicEntity.
 */
public interface PostgresMusicJpa extends JpaRepository<PostgresMusicEntity, String> {
}
