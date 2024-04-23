package com.github.enteraname74.musik.domain.model;

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
     * Check if the metadata is empty.
     *
     * @return true if the metadata is empty, false if not.
     */
    public Boolean isEmpty() {
        return this.album.isBlank() && this.artist.isBlank() && this.name.isBlank();
    }

    /**
     * Specify an empty music metadata element.
     *
     * @return an empty MusicMetadata.
     */
    public static MusicMetadata emptyMusicMetadata() {
        return new MusicMetadata();
    }
}
