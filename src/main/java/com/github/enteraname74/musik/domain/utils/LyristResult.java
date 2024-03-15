package com.github.enteraname74.musik.domain.utils;

/**
 * Result of a Lyrist API request.
 */
public class LyristResult {
    private final String lyrics;

    public LyristResult(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getLyrics() {
        return lyrics;
    }
}
