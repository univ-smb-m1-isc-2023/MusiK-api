package com.github.enteraname74.musik.domain.handler;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

/**
 * Manage the persistence of music files.
 */
public interface MusicFilePersistenceManager {

    /**
     * Tries to save a file and return its id.
     * The id of the file is its name without its extension.
     *
     * @param file the file to save.
     * @return the id of the file (its name, without its extension) or nothing if the file was not saved.
     */
    Optional<String> saveFile(MultipartFile file);

    /**
     * Tries to retrieve a music file from its id.
     *
     * @param id the id of the music file.
     * @return the found file or nothing.
     */
    Optional<File> getById(String id);
}