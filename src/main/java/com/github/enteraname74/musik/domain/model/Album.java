package com.github.enteraname74.musik.domain.model;

import java.util.List;

/**
 * Represent an Album.
 *
 * @param name the name of the album.
 * @param artist the artist of the album.
 * @param musics the musics of the album.
 * @param artworkUrl the cover of the album.
 */
public record Album(
        String name,
        String artist,
        List<Music> musics,
        String artworkUrl
) {
}
