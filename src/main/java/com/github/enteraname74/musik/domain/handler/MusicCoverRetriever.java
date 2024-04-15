package com.github.enteraname74.musik.domain.handler;

import java.io.File;
import java.util.Optional;

/**
 * Interface for retrieving covers of songs.
 */
public interface MusicCoverRetriever {

    /**
     * Retrieve the cover from an id.
     *
     * @param musicId the id of the element linked to the cover.
     * @return the file containing the cover or nothing if no covers where found.
     */
    Optional<byte[]> getById(String musicId);
}
