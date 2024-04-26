package com.github.enteraname74.musik.domain.model;

import java.util.List;
import java.util.Objects;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(name, album.name) && Objects.equals(artist, album.artist) && Objects.equals(musics, album.musics) && Objects.equals(artworkUrl, album.artworkUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artist, musics, artworkUrl);
    }
}
