package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.utils.ServiceResult;

/**
 * Service for managing authentication of users.
 */
public interface AuthService {

    /**
     * Tries to authenticate a User.
     *
     * @param name the name of the User.
     * @param password the password of the User.
     * @return a ServiceResult, with a connection token, of an error.
     */
    ServiceResult<?> authenticateUser(String name, String password);

    /**
     * Check if a user is authenticated.
     *
     * @param token the connection token to verify.
     * @return true if the user can still use his token, false if not.
     */
    boolean isUserAuthenticated(String token);

    /**
     * Increment the life of a token.
     *
     * @param token the token to update.
     */
    void incrementTokenLife(String token);
}
