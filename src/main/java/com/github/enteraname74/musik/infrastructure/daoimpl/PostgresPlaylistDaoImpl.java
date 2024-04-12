package com.github.enteraname74.musik.infrastructure.daoimpl;

import com.github.enteraname74.musik.domain.dao.PlaylistDao;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.model.Playlist;
import com.github.enteraname74.musik.infrastructure.jpa.PostgresMusicJpa;
import com.github.enteraname74.musik.infrastructure.jpa.PostgresPlaylistJpa;
import com.github.enteraname74.musik.infrastructure.model.PostgresMusicEntity;
import com.github.enteraname74.musik.infrastructure.model.PostgresPlaylistEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the MusicDao using a JpaRepository.
 */
@Component
public class PostgresPlaylistDaoImpl implements PlaylistDao {
    private final PostgresPlaylistJpa playlistJpa;
    private final PostgresMusicJpa musicJpa;

    @Autowired
    public PostgresPlaylistDaoImpl(PostgresPlaylistJpa playlistJpa, PostgresMusicJpa musicJpa) {
        this.playlistJpa = playlistJpa;
        this.musicJpa = musicJpa;
    }

    @Override
    public Optional<Playlist> getById(String id) {
        return playlistJpa.findById(id).map(PostgresPlaylistEntity::toPlaylist);
    }

    @Override
    public List<Playlist> getAll() {
        return playlistJpa.findAll().stream().map(PostgresPlaylistEntity::toPlaylist).toList();
    }

    @Override
    public Playlist upsert(Playlist element) {

        List<PostgresMusicEntity> musics = musicJpa.findAllById(
                element.getMusics().stream().map(Music::getId).toList()
        );

        return playlistJpa.save(PostgresPlaylistEntity.toPostgresPlaylistEntity(
                element,
                musics
        )).toPlaylist();
    }

    @Override
    public void deleteById(String id) {
        playlistJpa.deleteById(id);
    }
}
