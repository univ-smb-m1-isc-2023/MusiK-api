package com.github.enteraname74.musik.domain.repository;

import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.model.MusicMetadata;

/**
 * Repository for managing Musics.
 * Manage communication with the data source and add a level of abstraction behind it.
 */
public interface MusicRepository extends Repository<Music> {

    /**
     * Check if a music exists with the given metadata.
     *
     * @param metadata the metadata of the music to check.
     * @return true if the music exists, false if not.
     */
    Boolean doesMusicExistWithMetadata(MusicMetadata metadata);
}
