package com.github.enteraname74.musik.domain.utils;

import org.springframework.web.multipart.MultipartFile;

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
     * @param file the file to check.
     * @return true if the file is a music file, false if not.
     */
    public static boolean isMusicFile(MultipartFile file) {
        String contentType = file.getContentType();

        System.out.println("FILE TYPE: "+contentType);

        String[] validContentTypes = {"audio/mpeg", "audio/mp3", "audio/wav", "audio/flac", "audio/ogg", "audio/m4a", "audio/mp4"};

        for (String validType : validContentTypes) {
            if (validType.equals(contentType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieve the content-type of a file.
     *
     * @param fileName the name of the file.
     * @return the content-type of a file. The default one is application/octet-stream.
     */
    public static String getContentType(String fileName) {
        Optional<String> fileExtension = getFileExtension(fileName);

        return fileExtension.map(s -> switch (s) {
            case "mp3" -> "audio/mpeg";
            case "wav" -> "audio/wav";
            case "flac" -> "audio/flac";
            case "ogg" -> "audio/ogg";
            case "m4a" -> "audio/mp4";
            default -> "application/octet-stream"; // Default to binary data
        }).orElse("application/octet-stream");
    }
}
