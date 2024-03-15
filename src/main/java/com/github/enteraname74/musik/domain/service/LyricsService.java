package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.utils.ServiceResult;

/**
 * Service for retrieving lyrics of songs.
 */
public interface LyricsService {

    /**
     * Retrieves lyrics of a given music.
     *
     * @param musicId the id of the music where we want to retrieve lyrics.
     * @return a ServiceResult, indicating the result of the request.
     */
    ServiceResult<?> getLyricsFromMusic(String musicId);
}
