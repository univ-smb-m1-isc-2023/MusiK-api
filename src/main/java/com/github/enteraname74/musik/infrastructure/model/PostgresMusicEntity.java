package com.github.enteraname74.musik.infrastructure.model;

import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.utils.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representation of a Music used by a Postgres database.
 */
@Entity
@Table(name = "Music")
public class PostgresMusicEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "artist", nullable = false)
    private String artist;

    @Column(name = "album", nullable = false)
    private String album;

    public PostgresMusicEntity(String id, String path, String name, String artist, String album) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.artist = artist;
        this.album = album;
    }

    public PostgresMusicEntity() {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
     * Convert a PostgresMusicEntity to a Music.
     *
     * @return the representation of a PostgresMusicEntity as a Music.
     */
    public Music toMusic() {
        return new Music(
                this.id,
                this.path,
                this.name,
                this.artist,
                this.album
        );
    }

    /**
     * Convert a Music to a PostgresMusicEntity.
     *
     * @param music the music to convert.
     * @return the representation of a Music as a PostgresMusicEntity.
     */
    public static PostgresMusicEntity toPostgresMusicEntity(Music music) {
        return new PostgresMusicEntity(
                music.getId(),
                music.getPath(),
                music.getName(),
                music.getArtist(),
                music.getAlbum()
        );
    }
}
