package com.github.enteraname74.musik.domain.utils;

import java.util.Optional;

/**
 * Utils for files.
 */
public class FileUtils {

    /**
     * Tries to retrieve a file extension name.
     *
     * @param fileName the file name.
     * @return the potential extension name of the file.
     */
    public static Optional<String> getFileExtension(String fileName) {
        return Optional.ofNullable(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(fileName.lastIndexOf(".") + 1));
    }

    /**
     * Check if a file is a music file.
     *
     * @param fileName the name of the file.
     * @return true if the file is a music file, false if not.
     */
    public static boolean isMusicFile(String fileName) {
        Optional<String> extension = getFileExtension(fileName);
        if (extension.isEmpty()) return false;

        String foundExtension = extension.get();

        return foundExtension.equals("mp3") || foundExtension.equals("m4a");
    }
}
