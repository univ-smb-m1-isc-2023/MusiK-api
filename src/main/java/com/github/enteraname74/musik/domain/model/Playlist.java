package com.github.enteraname74.musik.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.enteraname74.musik.domain.utils.IdGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represent a Playlist.
 * It has a title and a list of musics.
 */
public class Playlist {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("musics")
    private ArrayList<Music> musics;

    public Playlist(String id, String title, ArrayList<Music> musics) {
        this.id = id;
        this.title = title;
        this.musics = musics;
    }

    /**
     * Empty constructor for Jackson.
     */
    public Playlist() {
        this(
                IdGenerator.generateRandomId(),
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Music> getMusics() {
        return musics;
    }

    public void setMusics(ArrayList<Music> musics) {
        this.musics = musics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(id, playlist.id) && Objects.equals(title, playlist.title) && Objects.equals(musics, playlist.musics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, musics);
    }
}
