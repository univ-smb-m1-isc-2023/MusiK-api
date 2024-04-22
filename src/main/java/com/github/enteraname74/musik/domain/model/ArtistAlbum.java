package com.github.enteraname74.musik.domain.model;

import java.util.Objects;

/**
 * Link an artist and an album.
 *
 * @param artist the name of the artist.
 * @param album the name of the album of the artist.
 */
public record ArtistAlbum(String artist, String album) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistAlbum that = (ArtistAlbum) o;
        return Objects.equals(artist, that.artist) && Objects.equals(album, that.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, album);
    }
}
