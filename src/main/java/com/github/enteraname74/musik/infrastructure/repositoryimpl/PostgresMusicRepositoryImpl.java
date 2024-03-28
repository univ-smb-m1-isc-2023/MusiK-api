package com.github.enteraname74.musik.infrastructure.repositoryimpl;

import com.github.enteraname74.musik.domain.dao.MusicDao;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.model.MusicMetadata;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the MusicRepository for a Postgres database.
 */
@Repository
public class PostgresMusicRepositoryImpl implements MusicRepository {

    private final MusicDao musicDao;

    @Autowired
    public PostgresMusicRepositoryImpl(MusicDao musicDao) {
        this.musicDao = musicDao;
    }

    @Override
    public List<Music> getAll() {
        return musicDao.getAll();
    }

    @Override
    public Optional<Music> getById(String id) {
        return musicDao.getById(id);
    }

    @Override
    public Music save(Music music) {
        return musicDao.upsert(music);
    }

    @Override
    public void deleteById(String id) {
        musicDao.deleteById(id);
    }

    @Override
    public boolean doesElementExists(String id) {
        return musicDao.getById(id).isPresent();
    }

    @Override
    public Boolean doesMusicExistWithMetadata(MusicMetadata metadata) {
        List<Music> allMusics = musicDao.getAll();

        return allMusics.stream().anyMatch(music -> music.hasSameMetadata(metadata));
    }
}
