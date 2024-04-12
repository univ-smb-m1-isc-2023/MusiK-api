package com.github.enteraname74.musik.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.enteraname74.musik.domain.utils.IdGenerator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @JsonProperty("playlistIds")
    private List<String> playlistIds;

    public Music(String id, String name, String artist, String album, String albumArtworkUrl, List<String> playlistIds) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.albumArtworkUrl = albumArtworkUrl;
        this.playlistIds = playlistIds;
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
                "",
                Collections.emptyList()
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

    public List<String> getPlaylistIds() {
        return playlistIds;
    }

    public void setPlaylistIds(List<String> playlistIds) {
        this.playlistIds = playlistIds;
    }

    /**
     * Build a Music object without information. It only holds the id of the music file.
     *
     * @param id the id of the music file.
     * @return a Music.
     */
    public static Music emptyMusicInformation(String id) {
        return new Music(id, "", "", "", "", Collections.emptyList());
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
                "",
                Collections.emptyList()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Music music = (Music) o;
        return Objects.equals(id, music.id) && Objects.equals(name, music.name) && Objects.equals(artist, music.artist) && Objects.equals(album, music.album) && Objects.equals(albumArtworkUrl, music.albumArtworkUrl) && Objects.equals(playlistIds, music.playlistIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artist, album, albumArtworkUrl, playlistIds);
    }
}
