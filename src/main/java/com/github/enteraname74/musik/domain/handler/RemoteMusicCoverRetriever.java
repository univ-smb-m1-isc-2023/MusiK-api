package com.github.enteraname74.musik.domain.handler;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Interface for retrieving music cover from a remote source.
 */
public interface RemoteMusicCoverRetriever {

    /**
     * Tries to retrieve a URL of the cover corresponding of a given music name and artist.
     *
     * @param musicName the name of the music.
     * @param musicArtist the name of the artist of the music.
     * @return The URL of the remote cover or nothing.
     */
    Optional<String> getCoverURL(String musicName, String musicArtist);
}
