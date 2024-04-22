package com.github.enteraname74.musik.domain.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Optional;

/**
 * Used to retrieve environment variables.
 */
public class EnvironmentVariable {
    /**
     * Tries to retrieve an environment variable from its key.
     *
     * @param key the key to use to retrieve a value.
     * @return the value of the key or nothing if the key is not correct.
     */
    public Optional<String> getFromKey(String key) {
        try {
            File envFile = new File("/app/auth/.env");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(envFile));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                Optional<String> optionalKey = getKey(line);
                if (optionalKey.isEmpty()) continue;

                String foundKey = optionalKey.get();
                if (!foundKey.equals(key)) continue;

                Optional<String> value = getValue(line);
                if (value.isEmpty()) continue;

                return value;
            }

        } catch (Exception e) {
            System.out.println("An exception has occurred when retrieving env var: " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }

    /**
     * Tries to retrieve a key from a field.
     *
     * @param field the field to retrieve the key.
     * @return the key or nothing if no key is linked to the field.
     */
    private Optional<String> getKey(String field) {
        try {
            return Optional.of(
                    field.split("=")[0]
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Tries to retrieve a value from a field.
     *
     * @param field the field to retrieve the value.
     * @return the value or nothing if no key is linked to the field.
     */
    private Optional<String> getValue(String field) {
        try {
            return Optional.of(
                    field.split("=")[1]
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
