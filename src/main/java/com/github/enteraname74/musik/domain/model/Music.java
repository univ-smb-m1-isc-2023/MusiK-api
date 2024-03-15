package com.github.enteraname74.musik.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.enteraname74.musik.domain.utils.IdGenerator;

/**
 * Represent a music and its information.
 */
public class Music {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("artist")
    private String artist;

    @JsonProperty("album")
    private String album;

    @JsonProperty("albumArtworkUrl")
    private String albumArtworkUrl;

    public Music(String id, String name, String artist, String album, String albumArtworkUrl) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.albumArtworkUrl = albumArtworkUrl;
    }

    /**
     * Empty constructor for Jackson.
     */
    public Music() {
        this(
                IdGenerator.generateRandomId(),
                "",
                "",
                "",
                ""
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAlbumArtworkUrl() {
        return albumArtworkUrl;
    }

    public void setAlbumArtworkUrl(String albumArtworkUrl) {
        this.albumArtworkUrl = albumArtworkUrl;
    }

    /**
     * Build a Music object without information. It only holds the id of the music file.
     *
     * @param id the id of the music file.
     * @return a Music.
     */
    public static Music emptyMusicInformation(String id) {
        return new Music(id, "", "", "", "");
    }

    /**
     * Build a Music object with information from a MusicMetadata element.
     *
     * @param id the id of the music file.
     * @param metadata the metadata used for the music file.
     * @return a Music.
     */
    public static Music ofMetadata(String id, MusicMetadata metadata) {
        return new Music(
                id,
                metadata.getName(),
                metadata.getArtist(),
                metadata.getAlbum(),
                ""
        );
    }

    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof Music music)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members

        boolean sameId = music.id.equals(this.id);
        boolean sameName = music.name.equals(this.name);
        boolean sameAlbum = music.album.equals(this.album);
        boolean sameArtist = music.artist.equals(this.artist);
        boolean sameAlbumArtwork = music.albumArtworkUrl.equals(this.albumArtworkUrl);

        return sameId && sameName && sameAlbum && sameArtist && sameAlbumArtwork;
    }
}
