package com.github.enteraname74.musik.domain.repository;

import com.github.enteraname74.musik.domain.model.Playlist;

/**
 * Repository for managing Playlists.
 * Manage communication with the data source and add a level of abstraction behind it.
 */
public interface PlaylistRepository extends Repository<Playlist> {

    /**
     * Add a music to a playlist.
     *
     * @param playlistId the id of the Playlist where we want to add the music.
     * @param musicId the id of the music to add to the playlist.
     * @return true if the music was added, false if not.
     */
    boolean addMusicToPlaylist(String playlistId, String musicId);

    /**
     * Remove a music from a music.
     *
     * @param playlistId the id of the Playlist where we want to remove the music.
     * @param musicId the id of the music to remove from the playlist.
     * @return true if the music was removed, false if not.
     */
    boolean removeMusicFromPlaylist(String playlistId, String musicId);
}
