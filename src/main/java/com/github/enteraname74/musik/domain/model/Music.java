package com.github.enteraname74.musik.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.enteraname74.musik.domain.utils.IdGenerator;

import java.util.ArrayList;
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
    private ArrayList<String> playlistIds;

    public Music(String id, String name, String artist, String album, String albumArtworkUrl, ArrayList<String> playlistIds) {
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
                new ArrayList<>()
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

    public ArrayList<String> getPlaylistIds() {
        return playlistIds;
    }

    public void setPlaylistIds(ArrayList<String> playlistIds) {
        this.playlistIds = playlistIds;
    }

    /**
     * Build a Music object with unknown information. It only holds the id of the music file.
     *
     * @param id the id of the music file.
     * @return a Music.
     */
    public static Music unknownMusicInformation(String id) {
        return new Music(id, "Unknown", "Unknown", "Unknown", "", new ArrayList<>());
    }

    /**
     * Build a Music object with information from a MusicMetadata element.
     *
     * @param id       the id of the music file.
     * @param metadata the metadata used for the music file.
     * @return a Music.
     */
    public static Music ofMetadata(String id, MusicMetadata metadata) {

        if (metadata.isEmpty()) {
            return Music.unknownMusicInformation(id);
        }

        return new Music(
                id,
                metadata.getName(),
                metadata.getArtist(),
                metadata.getAlbum(),
                "",
                new ArrayList<>()
        );
    }

    /**
     * Check if the music has the same metadata as a given MusicMetadata.
     *
     * @param metadata the metadata to use for the comparison
     * @return true if the music has the same metadata as the one given in the arguments.
     */
    public Boolean hasSameMetadata(MusicMetadata metadata) {
        return artist.equals(metadata.getArtist()) && album.equals(metadata.getAlbum()) && name.equals(metadata.getName());
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
