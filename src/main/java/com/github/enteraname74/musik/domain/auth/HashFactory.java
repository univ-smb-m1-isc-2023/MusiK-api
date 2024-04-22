package com.github.enteraname74.musik.domain.auth;

import java.util.Optional;

public interface HashFactory {

    /**
     * Build a HashedPassword from a given password.
     *
     * @param password the password to hash.
     * @return the HashedPassword resulted (hash + salt) or nothing if the hash couldn't be generated.
     */
    Optional<HashedPassword> toHashedPassword(String password);
}
