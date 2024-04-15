package com.github.enteraname74.musik.infrastructure.daoimpl;

import com.github.enteraname74.musik.domain.dao.MusicDao;
import com.github.enteraname74.musik.domain.model.Music;
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
public class PostgresMusicDaoImpl implements MusicDao {
    private final PostgresMusicJpa musicJpa;
    private final PostgresPlaylistJpa playlistJpa;

    @Autowired
    public PostgresMusicDaoImpl(PostgresMusicJpa musicJpa, PostgresPlaylistJpa playlistJpa) {
        this.musicJpa = musicJpa;
        this.playlistJpa = playlistJpa;
    }

    @Override
    public Optional<Music> getById(String id) {
        return musicJpa.findById(id).map(PostgresMusicEntity::toMusic);
    }

    @Override
    public List<Music> getAll() {
        return musicJpa.findAll().stream().map(PostgresMusicEntity::toMusic).toList();
    }

    @Override
    public Music upsert(Music music) {

        List<PostgresPlaylistEntity> playlists = playlistJpa.findAllById(
                music.getPlaylistIds()
        );

        return musicJpa.save(PostgresMusicEntity.toPostgresMusicEntity(
                music,
                playlists
        )).toMusic();
    }

    @Override
    public void deleteById(String id) {
        musicJpa.deleteById(id);
    }
}
