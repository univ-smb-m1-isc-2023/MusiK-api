package com.github.enteraname74.musik.domain.repository;

import com.github.enteraname74.musik.domain.model.Token;

/**
 * Repository for managing Tokens.
 * Manage communication with the data source and add a level of abstraction behind it.
 */
public interface TokenRepository extends Repository<Token> {

    /**
     * Check if a token is valid (correct and before max date usage).
     *
     * @param token the token to check.
     * @return true if the token is valid, false if not.
     */
    boolean isTokenValid(String token);

    /**
     * Increment the max life of a token by a fixed amount of time.
     *
     * @param token the token to update.
     */
    void incrementTokenLife(String token);

    /**
     * Clear invalid tokens.
     */
    void clearInvalidTokens();
}
