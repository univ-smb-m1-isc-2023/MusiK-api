package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.domain.handler.MusicFilePersistenceManager;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import com.github.enteraname74.musik.domain.repository.PlaylistRepository;
import com.github.enteraname74.musik.domain.service.MusicInformationService;
import com.github.enteraname74.musik.domain.utils.ServiceMessages;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the MusicService.
 */
@Service
public class MusicInformationServiceImpl implements MusicInformationService {

    private final MusicRepository musicRepository;
    private final PlaylistRepository playlistRepository;

    private final MusicFilePersistenceManager musicFilePersistenceManager;

    @Autowired
    public MusicInformationServiceImpl(
            MusicRepository musicRepository,
            PlaylistRepository playlistRepository,
            MusicFilePersistenceManager musicFilePersistenceManager
    ) {
        this.musicRepository = musicRepository;
        this.playlistRepository = playlistRepository;
        this.musicFilePersistenceManager = musicFilePersistenceManager;
    }

    @Override
    public List<Music> getAll() {
        return musicRepository.getAll();
    }

    @Override
    public ServiceResult<?> getById(String id) {
        if (!musicRepository.doesElementExists(id)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_MUSIC_ID
            );
        }

        return new ServiceResult<>(
                HttpStatus.OK,
                musicRepository.getById(id)
        );
    }

    @Override
    public ServiceResult<?> save(Music music) {
        try {
            Music savedMusic = musicRepository.save(music);
            return new ServiceResult<>(
                    HttpStatus.ACCEPTED,
                    savedMusic
            );
        } catch (Exception exception) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.MUSIC_CANNOT_BE_SAVED
            );
        }
    }

    @Override
    public ServiceResult<?> deleteById(String id) {
        if (!musicRepository.doesElementExists(id)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_MUSIC_ID
            );
        }

        // We first need to remove the apparition of this music in playlists :
        Music musicToDelete = musicRepository.getById(id).get();

        musicToDelete.getPlaylistIds().forEach(playlistId -> playlistRepository.removeMusicFromPlaylist(playlistId, id));

        musicRepository.deleteById(id);
        musicFilePersistenceManager.deleteFile(id);
        return new ServiceResult<>(
                HttpStatus.OK,
                ServiceMessages.MUSIC_DELETED
        );
    }
}
