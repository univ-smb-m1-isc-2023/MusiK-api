package com.github.enteraname74.musik.domain.auth;


import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Optional;

/**
 * Implementation of the HashFactory using SHA-512.
 */
@Component
public class ShaHashFactoryImpl implements HashFactory {
    @Override
    public Optional<HashedPassword> toHashedPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] salt = generateSalt();
            md.update(salt);

            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            return Optional.of(
                    new HashedPassword(
                            hashedPassword,
                            salt
                    )
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    /**
     * Generate a salt to use for hashing the password.
     *
     * @return the salt to use for the password hashing.
     */
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }
}
