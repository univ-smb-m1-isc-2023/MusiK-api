package com.github.enteraname74.musik.domain.handlerimpl;

import com.github.enteraname74.musik.domain.handler.MusicFileMetadataManager;
import com.github.enteraname74.musik.domain.model.MusicMetadata;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Implementation of the MusicFileMetadataManager using Jaudiotagger.
 */
@Component
public class MusicFileMetadataManagerImpl implements MusicFileMetadataManager {

    @Override
    public MusicMetadata getMetadataOfFile(File musicFile) {
        try {
            AudioFile audioFile = AudioFileIO.read(musicFile);
            Tag tag = audioFile.getTag();

            return new MusicMetadata(
                    tag.getFirst(FieldKey.TITLE),
                    tag.getFirst(FieldKey.ARTIST),
                    tag.getFirst(FieldKey.ALBUM)
            );

        } catch (Exception e) {
            return MusicMetadata.emptyMusicMetadata();
        }
    }
}
