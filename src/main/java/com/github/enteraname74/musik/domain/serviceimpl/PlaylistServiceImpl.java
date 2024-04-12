package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.controller.utils.ControllerMessages;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.model.Playlist;
import com.github.enteraname74.musik.domain.repository.PlaylistRepository;
import com.github.enteraname74.musik.domain.service.PlaylistService;
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

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
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
                    ControllerMessages.WRONG_PLAYLIST_ID
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
                    HttpStatus.ACCEPTED,
                    savedPlaylist
            );
        } catch (Exception exception) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ControllerMessages.PLAYLIST_CANNOT_BE_SAVED
            );
        }
    }

    @Override
    public ServiceResult<?> deleteById(String id) {
        if (!playlistRepository.doesElementExists(id)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ControllerMessages.WRONG_PLAYLIST_ID
            );
        }
        playlistRepository.deleteById(id);
        return new ServiceResult<>(
                HttpStatus.OK,
                ControllerMessages.PLAYLIST_DELETED
        );
    }

    @Override
    public ServiceResult<?> addMusicToPlaylist(String playlistId, String musicId) {
        return null;
    }

    @Override
    public ServiceResult<?> removeMusicToPlaylist(String playlistId, String musicId) {
        return null;
    }
}
