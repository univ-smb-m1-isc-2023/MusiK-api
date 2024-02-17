package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.controller.utils.ControllerMessages;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import com.github.enteraname74.musik.domain.service.MusicService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the MusicService.
 */
@Service
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;

    @Autowired
    public MusicServiceImpl(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
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
                    ControllerMessages.WRONG_MUSIC_ID
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
                    ControllerMessages.MUSIC_CANNOT_BE_SAVED
            );
        }
    }

    @Override
    public ServiceResult<?> deleteById(String id) {
        if (!musicRepository.doesElementExists(id)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ControllerMessages.WRONG_MUSIC_ID
            );
        }
        musicRepository.deleteById(id);
        return new ServiceResult<>(
                HttpStatus.OK,
                ControllerMessages.MUSIC_DELETED
        );
    }
}
