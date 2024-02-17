package com.github.enteraname74.musik.infrastructure;

import com.github.enteraname74.musik.infrastructure.repositoryimpl.PostgresMusicRepositoryImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class for the Postgres database.
 */
@EnableJpaRepositories(
        basePackageClasses = {
                PostgresMusicRepositoryImpl.class
        }
)
public class PostgresDatabaseConfiguration {
}
