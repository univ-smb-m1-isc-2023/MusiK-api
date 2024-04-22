package com.github.enteraname74.musik.infrastructure.jpa;

import com.github.enteraname74.musik.infrastructure.model.PostgresTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that will provide methods for managing PostgresTokenEntity.
 */
public interface PostgresTokenJpa extends JpaRepository<PostgresTokenEntity, String> {
}
