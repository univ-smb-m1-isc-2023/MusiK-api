package com.github.enteraname74.musik.infrastructure.jpa;

import com.github.enteraname74.musik.infrastructure.model.PostgresUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that will provide methods for managing PostgresUserEntities.
 */
public interface PostgresUserJpa extends JpaRepository<PostgresUserEntity, String > {
}
