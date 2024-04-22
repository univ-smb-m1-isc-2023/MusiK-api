package com.github.enteraname74.musik.domain.model;

import java.util.List;

/**
 * Represent an Artist.
 *
 * @param name the name of the artist.
 * @param musics the musics of the artist.
 * @param artworkUrl the cover of the artist.
 */
public record Artist(
        String name,
        List<Music> musics,
        String artworkUrl
) {
}
