package com.github.enteraname74.musik.infrastructure;

import com.github.enteraname74.musik.infrastructure.repositoryimpl.PostgresMusicRepositoryImpl;
import com.github.enteraname74.musik.infrastructure.repositoryimpl.PostgresPlaylistRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class for the Postgres database.
 */
@EnableJpaRepositories(
        basePackageClasses = {
                PostgresMusicRepositoryImpl.class,
                PostgresPlaylistRepositoryImpl.class
        }
)
public class PostgresDatabaseConfiguration {
}
