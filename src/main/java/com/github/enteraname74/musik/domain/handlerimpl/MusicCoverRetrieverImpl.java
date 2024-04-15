package com.github.enteraname74.musik.domain.handlerimpl;

import com.github.enteraname74.musik.domain.handler.MusicCoverRetriever;
import com.github.enteraname74.musik.domain.handler.MusicFilePersistenceManager;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.images.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

/**
 * Implementation of the CoverRetriever.
 */
@Component
public class MusicCoverRetrieverImpl implements MusicCoverRetriever {

    private final MusicFilePersistenceManager musicFilePersistenceManager;

    @Autowired
    public MusicCoverRetrieverImpl(MusicFilePersistenceManager musicFilePersistenceManager) {
        this.musicFilePersistenceManager = musicFilePersistenceManager;
    }

    @Override
    public Optional<byte[]> getById(String musicId) {
        try {
            Optional<File> optionalMusicFile = musicFilePersistenceManager.getById(musicId);
            if (optionalMusicFile.isEmpty()) return Optional.empty();

            AudioFile audioFile = AudioFileIO.read(optionalMusicFile.get());

            Artwork musicArtwork = audioFile.getTag().getFirstArtwork();

            return Optional.of(musicArtwork.getBinaryData());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
