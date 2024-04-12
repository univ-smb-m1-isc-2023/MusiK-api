package com.github.enteraname74.musik.infrastructure.model;

import com.github.enteraname74.musik.domain.model.Playlist;
import com.github.enteraname74.musik.domain.utils.IdGenerator;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Playlist")
public class PostgresPlaylistEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<PostgresMusicEntity> musics;

    public PostgresPlaylistEntity(String id, String title, List<PostgresMusicEntity> musics) {
        this.id = id;
        this.title = title;
        this.musics = musics;
    }

    public PostgresPlaylistEntity() {
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

    public void setTitle(String name) {
        this.title = name;
    }

    public List<PostgresMusicEntity> getMusics() {
        return musics;
    }

    public void setMusics(List<PostgresMusicEntity> musics) {
        this.musics = musics;
    }

    /**
     * Convert a PostgresPlaylist to a Playlist.
     *
     * @return the representation of a PostgresPlaylist as a Playlist.
     */
    public Playlist toPlaylist() {
        return new Playlist(
                this.id,
                this.title,
                this.musics.stream().map(PostgresMusicEntity::toMusic).toList()
        );
    }

    /**
     * Convert a Playlist to a PostgresPlaylistEntity.
     *
     * @param playlist the Playlist to convert.
     * @return the representation of a Playlist as a PostgresPlaylistEntity.
     */
    public static PostgresPlaylistEntity toPostgresPlaylistEntity(
            Playlist playlist
    ) {
        return new PostgresPlaylistEntity(
                playlist.getId(),
                playlist.getTitle(),
                Collections.emptyList()
        );
    }

    /**
     * Convert a Playlist to a PostgresPlaylist.
     *
     * @param playlist the Playlist to convert.
     * @return the representation of a Playlist as a PostgresPlaylist.
     */
    public static PostgresPlaylistEntity toPostgresPlaylistEntity(
            Playlist playlist,
            List<PostgresMusicEntity> musics
    ) {
        return new PostgresPlaylistEntity(
                playlist.getId(),
                playlist.getTitle(),
                musics
        );
    }
}
