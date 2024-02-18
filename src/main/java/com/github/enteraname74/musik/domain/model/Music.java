package com.github.enteraname74.musik.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.enteraname74.musik.domain.utils.IdGenerator;

import java.util.UUID;

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
}
