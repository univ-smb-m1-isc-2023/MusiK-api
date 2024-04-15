package com.github.enteraname74.musik.infrastructure.jpa;

import com.github.enteraname74.musik.infrastructure.model.PostgresPlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that will provide methods for managing PostgresPlaylistEntity.
 */
public interface PostgresPlaylistJpa extends JpaRepository<PostgresPlaylistEntity, String> {
}