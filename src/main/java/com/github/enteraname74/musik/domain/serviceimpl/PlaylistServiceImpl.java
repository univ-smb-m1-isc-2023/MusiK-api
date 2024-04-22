package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.domain.model.Playlist;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import com.github.enteraname74.musik.domain.repository.PlaylistRepository;
import com.github.enteraname74.musik.domain.service.PlaylistService;
import com.github.enteraname74.musik.domain.utils.ServiceMessages;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the PlaylistService.
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, MusicRepository musicRepository) {
        this.playlistRepository = playlistRepository;
        this.musicRepository = musicRepository;
    }

    @Override
    public List<Playlist> getAll() {
        return playlistRepository.getAll();
    }

    @Override
    public ServiceResult<?> getById(String id) {
        if (!playlistRepository.doesElementExists(id)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_PLAYLIST_ID
            );
        }

        return new ServiceResult<>(
                HttpStatus.OK,
                playlistRepository.getById(id)
        );
    }

    @Override
    public ServiceResult<?> save(Playlist playlist) {
        try {
            Playlist savedPlaylist = playlistRepository.save(playlist);
            return new ServiceResult<>(
                    HttpStatus.CREATED,
                    savedPlaylist
            );
        } catch (Exception exception) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.PLAYLIST_CANNOT_BE_SAVED
            );
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public ServiceResult<?> deleteById(String id) {
        if (!playlistRepository.doesElementExists(id)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_PLAYLIST_ID
            );
        }

        // We first need to remove the musics from the playlist.
        Playlist playlistToDelete = playlistRepository.getById(id).get();
        playlistToDelete.getMusics().forEach( music -> playlistRepository.removeMusicFromPlaylist(id, music.getId()));

        playlistRepository.deleteById(id);
        return new ServiceResult<>(
                HttpStatus.OK,
                ServiceMessages.PLAYLIST_DELETED
        );
    }

    @Override
    public ServiceResult<?> addMusicToPlaylist(String playlistId, String musicId) {
        if (!playlistRepository.doesElementExists(playlistId)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_PLAYLIST_ID
            );
        }
        if (!musicRepository.doesElementExists(musicId)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_MUSIC_ID
            );
        }

        if (!playlistRepository.addMusicToPlaylist(playlistId,musicId)) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.MUSIC_ALREADY_IN_PLAYLIST
            );
        } else {
            return new ServiceResult<>(
                    HttpStatus.OK,
                    ServiceMessages.MUSIC_ADDED_IN_PLAYLIST
            );
        }
    }

    @Override
    public ServiceResult<?> removeMusicToPlaylist(String playlistId, String musicId) {
        if (!playlistRepository.doesElementExists(playlistId)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_PLAYLIST_ID
            );
        }
        if (!musicRepository.doesElementExists(musicId)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_MUSIC_ID
            );
        }

        if (!playlistRepository.removeMusicFromPlaylist(playlistId,musicId)) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.MUSIC_NOT_FOUND_IN_PLAYLIST
            );
        } else {
            return new ServiceResult<>(
                    HttpStatus.OK,
                    ServiceMessages.MUSIC_REMOVED_FROM_PLAYLIST
            );
        }
    }
}
