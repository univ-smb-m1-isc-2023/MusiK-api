package com.github.enteraname74.musik.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.enteraname74.musik.domain.utils.IdGenerator;

import java.util.Collections;
import java.util.List;

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
    private List<Music> musics;

    public Playlist(String id, String title, List<Music> musics) {
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
                Collections.emptyList()
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

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }
}
