package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.controller.utils.ControllerMessages;
import com.github.enteraname74.musik.domain.handler.MusicFilePersistenceManager;
import com.github.enteraname74.musik.domain.service.MusicFileService;
import com.github.enteraname74.musik.domain.utils.FileUtils;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Implementation of the MusicFileService.
 * Music files are saved in a specific folder.
 */
@Service
public class MusicFileServiceImpl implements MusicFileService {
    private final MusicFilePersistenceManager musicFilePersistenceManager;

    @Autowired
    public MusicFileServiceImpl(MusicFilePersistenceManager musicFilePersistenceManager) {
        this.musicFilePersistenceManager = musicFilePersistenceManager;
    }

    @Override
    public ServiceResult<?> save(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        if (!FileUtils.isMusicFile(fileName)) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ControllerMessages.FILE_IS_NOT_A_MUSIC
            );
        }

        Optional<String> savedFileName = musicFilePersistenceManager.saveFile(file);

        return savedFileName.<ServiceResult<?>>map(s -> new ServiceResult<>(
                HttpStatus.ACCEPTED,
                s
        )).orElseGet(() -> new ServiceResult<>(
                HttpStatus.BAD_REQUEST,
                ControllerMessages.MUSIC_CANNOT_BE_SAVED
        ));
    }

    @Override
    public ServiceResult<?> getById(String id) {
        return null;
    }
}
