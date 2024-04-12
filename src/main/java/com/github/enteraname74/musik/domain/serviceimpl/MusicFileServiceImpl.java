package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.controller.utils.ControllerMessages;
import com.github.enteraname74.musik.domain.handler.MusicFilePersistenceManager;
import com.github.enteraname74.musik.domain.handler.MusicInformationRetriever;
import com.github.enteraname74.musik.domain.handler.RemoteMusicCoverRetriever;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import com.github.enteraname74.musik.domain.service.MusicFileService;
import com.github.enteraname74.musik.domain.utils.FileUtils;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
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

    private final ServerProperties serverProperties;

    @Autowired
    public MusicFileServiceImpl(
            MusicFilePersistenceManager musicFilePersistenceManager,
            MusicRepository musicRepository, RemoteMusicCoverRetriever remoteMusicCoverRetriever,
            MusicInformationRetriever musicInformationRetriever, ServerProperties serverProperties
    ) {
        this.musicFilePersistenceManager = musicFilePersistenceManager;
        this.musicRepository = musicRepository;
        this.remoteMusicCoverRetriever = remoteMusicCoverRetriever;
        this.musicInformationRetriever = musicInformationRetriever;
        this.serverProperties = serverProperties;
    }

    @Override
    public ServiceResult<?> save(MultipartFile file) {
        if (!FileUtils.isMusicFile(file)) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ControllerMessages.FILE_IS_NOT_A_MUSIC
            );
        }
        // We first save the music file.
        Optional<String> savedFilePotentialId = musicFilePersistenceManager.saveFile(file);

        if (savedFilePotentialId.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ControllerMessages.MUSIC_CANNOT_BE_SAVED
            );
        }

        String savedFileId = savedFilePotentialId.get();

        // We then retrieve music file information.
        Optional<File> musicFile = musicFilePersistenceManager.getById(savedFileId);

        if (musicFile.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ControllerMessages.MUSIC_CANNOT_BE_SAVED
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
        urlToSave = remoteCoverUrl.orElseGet(() -> buildLocalCoverURL(musicInformation.getId()));

        musicInformation.setAlbumArtworkUrl(urlToSave);

        // Finally, we save the music information.
        musicRepository.save(musicInformation);

        return new ServiceResult<>(HttpStatus.ACCEPTED, savedFileId);
    }

    /**
     * Build a local cover URL from the musicId.
     *
     * @param musicId the id of the music.
     * @return a URL to use for retrieving the cover of the music.
     */
    private String buildLocalCoverURL(String musicId) {
        return serverProperties.getAddress().getHostAddress()+":"+serverProperties.getPort()+"/cover/"+musicId;
    }

    @Override
    public ServiceResult<?> getById(String id) {
        Optional<File> musicFile = musicFilePersistenceManager.getById(id);

        if (musicFile.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ControllerMessages.WRONG_MUSIC_ID
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
