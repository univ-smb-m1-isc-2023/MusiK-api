package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.domain.utils.ServiceMessages;
import com.github.enteraname74.musik.domain.handler.MusicFilePersistenceManager;
import com.github.enteraname74.musik.domain.handler.MusicInformationRetriever;
import com.github.enteraname74.musik.domain.handler.RemoteMusicCoverRetriever;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.model.MusicMetadata;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import com.github.enteraname74.musik.domain.service.MusicFileService;
import com.github.enteraname74.musik.domain.utils.FileUtils;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

/**
 * Implementation of the MusicFileService.
 * Music files are saved in a specific folder.
 */
@Service
public class MusicFileServiceImpl implements MusicFileService {
    private final MusicFilePersistenceManager musicFilePersistenceManager;
    private final MusicRepository musicRepository;

    private final RemoteMusicCoverRetriever remoteMusicCoverRetriever;

    private final MusicInformationRetriever musicInformationRetriever;


    @Autowired
    public MusicFileServiceImpl(
            MusicFilePersistenceManager musicFilePersistenceManager,
            MusicRepository musicRepository, RemoteMusicCoverRetriever remoteMusicCoverRetriever,
            MusicInformationRetriever musicInformationRetriever
    ) {
        this.musicFilePersistenceManager = musicFilePersistenceManager;
        this.musicRepository = musicRepository;
        this.remoteMusicCoverRetriever = remoteMusicCoverRetriever;
        this.musicInformationRetriever = musicInformationRetriever;
    }

    @Override
    public ServiceResult<?> save(MultipartFile file) {
        if (!FileUtils.isMusicFile(file)) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.FILE_IS_NOT_A_MUSIC
            );
        }
        // We first save the music file.
        Optional<String> savedFilePotentialId = musicFilePersistenceManager.saveFile(file);

        if (savedFilePotentialId.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.MUSIC_CANNOT_BE_SAVED
            );
        }

        String savedFileId = savedFilePotentialId.get();

        // We then retrieve music file information.
        Optional<File> musicFile = musicFilePersistenceManager.getById(savedFileId);

        if (musicFile.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.MUSIC_CANNOT_BE_SAVED
            );
        }
        Music musicInformation = musicInformationRetriever.getInformationAboutMusicFile(
                musicFile.get(),
                savedFileId
        );

        Optional<String> remoteCoverUrl = remoteMusicCoverRetriever.getCoverURL(
                musicInformation.getName(),
                musicInformation.getArtist()
        );

        String urlToSave;
        urlToSave = remoteCoverUrl.orElse("");

        musicInformation.setAlbumArtworkUrl(urlToSave);

        System.out.println("Url of the cover art: "+musicInformation.getAlbumArtworkUrl());

        // If the music already exists, we do nothing.
        if (musicRepository.doesMusicExistWithMetadata(MusicMetadata.ofMusic(musicInformation))) {
            musicFilePersistenceManager.deleteFile(musicInformation.getId());
            return new ServiceResult<>(HttpStatus.CONFLICT, ServiceMessages.MUSIC_ALREADY_SAVED);
        }

        // Finally, we save the music information.
        musicRepository.save(musicInformation);

        return new ServiceResult<>(HttpStatus.ACCEPTED, savedFileId);
    }

    @Override
    public ServiceResult<?> getById(String id) {
        Optional<File> musicFile = musicFilePersistenceManager.getById(id);

        if (musicFile.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_MUSIC_ID
            );
        } else {
            System.out.println("GOT FILE: "+musicFile.get().length());
            return new ServiceResult<>(
                    HttpStatus.OK,
                    musicFile.get()
            );
        }
    }
}
