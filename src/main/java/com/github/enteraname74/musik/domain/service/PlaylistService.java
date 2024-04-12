package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.model.Playlist;
import com.github.enteraname74.musik.domain.utils.ServiceResult;

import java.util.List;

/**
 * Service used to manage playlists.
 */
public interface PlaylistService {
    /**
     * Retrieves all Playlists.
     * If a problem occurs, return an empty list.
     *
     * @return a list containing all Playlists.
     */
    List<Playlist> getAll();

    /**
     * Retrieves a Playlist from its id.
     *
     * @param id the id of the Playlist to retrieve.
     * @return a ServiceResult, holding the found Playlist or an error.
     */
    ServiceResult<?> getById(String id);

    /**
     * Save a new Playlist.
     *
     * @param playlist the Playlist to save.
     * @return a ServiceResult, holding the saved Playlist or an error.
     */
    ServiceResult<?> save(Playlist playlist);

    /**
     * Delete a Playlist from its id.
     *
     * @param id the id of the Playlist to delete.
     * @return a ServiceResult, holding the response of the request or an error.
     */
    ServiceResult<?> deleteById(String id);

    /**
     * Add a music to a playlist.
     *
     * @param playlistId the id of the Playlist where we want to add the music.
     * @param musicId the id of the music to add to the playlist.
     * @return a ServiceResult, holding the response of the request or an error.
     */
    ServiceResult<?> addMusicToPlaylist(String playlistId, String musicId);

    /**
     * Remove a music from a music.
     *
     * @param playlistId the id of the Playlist where we want to remove the music.
     * @param musicId the id of the music to remove from the playlist.
     * @return a ServiceResult, holding the response of the request or an error.
     */
    ServiceResult<?> removeMusicToPlaylist(String playlistId, String musicId);
}
