package com.github.enteraname74.musik.domain.handler;

import java.util.Optional;

/**
 * Interface for creating and managing fingerprints of songs.
 */
public interface MusicFingerprintReader {

    /**
     * Retrieve the fingerprint of a music.
     *
     * @param musicPath the path of the music.
     * @return the potential fingerprint or nothing.
     */
    Optional<String> getFingerPrintFromMusic(String musicPath);
}
