package com.github.enteraname74.musik.domain.auth;

/**
 * Check that a password is valid.
 */
public interface PasswordVerification {

    /**
     * Check if a password is matching a hashed one.
     *
     * @param password the password to check.
     * @param hashedPassword the supposedly equivalent of the password, but hashed.
     * @return true if the password is matching, false if not.
     */
    boolean isMatching(String password, HashedPassword hashedPassword);
}
