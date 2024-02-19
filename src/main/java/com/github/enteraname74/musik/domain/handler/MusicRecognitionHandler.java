package com.github.enteraname74.musik.domain.handler;

import com.github.enteraname74.musik.domain.model.Music;

import java.util.Optional;

/**
 * Abstract class for music recognition.
 * This aims to build a fingerprint of a song and analyze it to fetch metadata of it such as artist, album and song name.
 */
public abstract class MusicRecognitionHandler {

    /**
     * Retrieve the fingerprint of a music.
     *
     * @param musicPath the path of the music.
     * @return the potential fingerprint or nothing.
     */
    abstract protected Optional<String> getFingerPrintFromMusic(String musicPath);

    Optional<Music> getMusicInformation(String musicPath) {
        Optional<String> fingerprint = getFingerPrintFromMusic(musicPath);

        if (fingerprint.isEmpty()) return Optional.empty();

        return Optional.empty();
    }
}
