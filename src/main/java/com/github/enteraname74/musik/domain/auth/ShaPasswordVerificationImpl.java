package com.github.enteraname74.musik.domain.auth;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Implementation of the PasswordVerification using SHA-512.
 */
@Component
public class ShaPasswordVerificationImpl implements PasswordVerification{
    @Override
    public boolean isMatching(String password, HashedPassword hashedPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(hashedPassword.salt());

            byte[] hashToCompare = md.digest(password.getBytes(StandardCharsets.UTF_8));

            return Arrays.equals(hashToCompare, hashedPassword.hash());
        } catch (Exception e) {
            return false;
        }
    }
}
