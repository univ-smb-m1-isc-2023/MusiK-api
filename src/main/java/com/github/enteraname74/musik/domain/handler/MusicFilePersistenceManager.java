package com.github.enteraname74.musik.domain.handler;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Manage the persistence of music files.
 */
public interface MusicFilePersistenceManager {

    /**
     * Tries to save a file and return its name.
     * The name of the file should be its id.
     *
     * @param file the file to save.
     * @return the name of the file or nothing if the file was not saved.
     */
    Optional<String> saveFile(MultipartFile file);
}