package com.github.enteraname74.musik.domain.handler;

import com.github.enteraname74.musik.domain.model.Music;

import java.io.File;

/**
 * Interface for retrieving information about a music file.
 */
public interface MusicInformationRetriever {

    /**
     * Tries to retrieve information (artist, album, name...) about a music file.
     *
     * @param musicFile the file to analyze.
     * @param musicFileId the id of the file used in for the returned Music object.
     * @return a Music object, holding information about the music file.
     */
    Music getInformationAboutMusicFile(File musicFile, String musicFileId);
}
