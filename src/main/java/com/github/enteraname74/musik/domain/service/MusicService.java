package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.utils.ServiceResult;

import java.util.List;

/**
 * Service for managing Musics.
 * Used to check if given information are correct, manage returned data and response code.
 */
public interface MusicService {

    /**
     * Retrieves all Musics.
     * If a problem occurs, return an empty list.
     *
     * @return a list containing all Musics.
     */
    List<Music> getAll();

    /**
     * Retrieves a Music from its id.
     *
     * @param id the id of the Music to retrieve.
     * @return a ServiceResult, holding the found Music or an error.
     */
    ServiceResult<?> getById(String id);

    /**
     * Save a new Music.
     *
     * @param music the Music to save.
     * @return a ServiceResult, holding the saved Music or an error.
     */
    ServiceResult<?> save(Music music);

    /**
     * Delete a Music from its id.
     *
     * @param id the id of the Music to delete.
     * @return a ServiceResult, holding the response of the request or an error.
     */
    ServiceResult<?> deleteById(String id);
}
