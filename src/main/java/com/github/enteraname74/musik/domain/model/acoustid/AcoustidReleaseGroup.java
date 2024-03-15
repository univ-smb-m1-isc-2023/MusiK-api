package com.github.enteraname74.musik.domain.model.acoustid;

import java.util.List;

/**
 * Represent a release from the Acoustid Api.
 * A release holds information about an album or a single for example.
 */
public class AcoustidReleaseGroup {
    private List<AcoustidArtist> artists;
    private String id;
    private String title;
    private String type;

    public AcoustidReleaseGroup(List<AcoustidArtist> artists, String id, String title, String type) {
        this.artists = artists;
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public AcoustidReleaseGroup() {
    }

    public List<AcoustidArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<AcoustidArtist> artists) {
        this.artists = artists;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
