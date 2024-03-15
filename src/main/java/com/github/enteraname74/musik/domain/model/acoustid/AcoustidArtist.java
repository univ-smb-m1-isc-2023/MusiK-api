package com.github.enteraname74.musik.domain.model.acoustid;

/**
 * Represent an artist from the Acoustid Api.
 */
public class AcoustidArtist {
    private String id;

    private String name;

    public AcoustidArtist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public AcoustidArtist() {
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
}
