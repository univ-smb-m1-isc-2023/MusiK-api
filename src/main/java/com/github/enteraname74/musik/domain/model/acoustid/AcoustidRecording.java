package com.github.enteraname74.musik.domain.model.acoustid;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represent a recording from the Acoustid Api.
 * A recording holds data about the name of the song, its artists and its featured albums.
 */
public class AcoustidRecording {
    private List<AcoustidArtist> artists;
    private int duration;
    private String id;
    @SerializedName("releasegroups")
    private List<AcoustidReleaseGroup> releaseGroups;
    private String title;

    public AcoustidRecording(List<AcoustidArtist> artists, int duration, String id, List<AcoustidReleaseGroup> releaseGroups, String title) {
        this.artists = artists;
        this.duration = duration;
        this.id = id;
        this.releaseGroups = releaseGroups;
        this.title = title;
    }

    public AcoustidRecording() {
    }

    public List<AcoustidArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<AcoustidArtist> artists) {
        this.artists = artists;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AcoustidReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }

    public void setReleaseGroups(List<AcoustidReleaseGroup> releaseGroups) {
        this.releaseGroups = releaseGroups;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
