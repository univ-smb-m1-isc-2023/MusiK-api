package com.github.enteraname74.musik.domain.handler;

import com.github.enteraname74.musik.domain.model.MusicMetadata;

import java.io.File;

/**
 * Interface for managing the metadata of a music file.
 */
public interface MusicFileMetadataManager {

    /**
     * Retrieves the metadata of a file.
     * It can return
     *
     * @param musicFile the file containing the metadata to extract.
     * @return a MusicMetadata element, containing the found metadata.
     */
    MusicMetadata getMetadataOfFile(File musicFile);
}
