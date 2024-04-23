package com.github.enteraname74.musik.domain.model;

import java.util.Objects;

/**
 * Represent the metadata of a music file.
 */
public class MusicMetadata {
    private String name;
    private String artist;
    private String album;

    public MusicMetadata() {
        this.name = "";
        this.artist = "";
        this.album = "";
    }

    public MusicMetadata(String name, String artist, String album) {
        this.name = name;
        this.artist = artist;
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Check if the metadata are empty.
     *
     * @return true if the metadata are empty, false if not.
     */
    public Boolean isEmpty() {
        return name.isBlank() && artist.isBlank() && album.isBlank();
    }

    /**
     * Specify an empty music metadata element.
     *
     * @return an empty MusicMetadata.
     */
    public static MusicMetadata emptyMusicMetadata() {
        return new MusicMetadata();
    }

    /**
     * Build a MusicMetadata from a given music.
     *
     * @param music the music to build metadata from.
     * @return a MusicMetadata based on the given music.
     */
    public static MusicMetadata ofMusic(Music music) {
        return new MusicMetadata(
                music.getName(),
                music.getArtist(),
                music.getAlbum()
        );
    }

    @Override
    public String toString() {
        return "MusicMetadata(name = "+name+", artist = "+artist+", album = "+album+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicMetadata that = (MusicMetadata) o;
        return Objects.equals(name, that.name) && Objects.equals(artist, that.artist) && Objects.equals(album, that.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artist, album);
    }
}
