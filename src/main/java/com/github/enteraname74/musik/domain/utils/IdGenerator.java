package com.github.enteraname74.musik.domain.utils;

import java.security.SecureRandom;

/**
 * Utils for managing ids.
 */
public class IdGenerator {
    private static final String ID_STRING_GENERATOR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Integer MAX_STRING_SIZE = 64;

    /**
     * Generate a random and unique id.
     *
     * @return a random and unique id.
     */
    public static String generateRandomId() {
        SecureRandom randomGenerator = new SecureRandom();
        String newId = "";
        for (int i = 0; i < MAX_STRING_SIZE; i++) {
            String newCharacter = String.valueOf(ID_STRING_GENERATOR.charAt(randomGenerator.nextInt(ID_STRING_GENERATOR.length())));
            newId = newId.concat(newCharacter);
        }
        return newId;
    }
}
