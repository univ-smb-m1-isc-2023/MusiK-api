package com.github.enteraname74.musik.infrastructure.repositoryimpl;

import com.github.enteraname74.musik.domain.dao.MusicDao;
import com.github.enteraname74.musik.domain.dao.PlaylistDao;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.model.Playlist;
import com.github.enteraname74.musik.domain.repository.PlaylistRepository;
import com.github.enteraname74.musik.domain.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the PlaylistRepository for a Postgres database.
 */
@Component
public class PostgresPlaylistRepositoryImpl implements PlaylistRepository {

    private final PlaylistDao playlistDao;
    private final MusicDao musicDao;

    @Autowired
    public PostgresPlaylistRepositoryImpl(PlaylistDao playlistDao, MusicDao musicDao) {
        this.playlistDao = playlistDao;
        this.musicDao = musicDao;
    }

    @Override
    public boolean addMusicToPlaylist(String playlistId, String musicId) {
        Optional<Playlist> optionalPlaylist = playlistDao.getById(playlistId);
        Optional<Music> optionalMusic = musicDao.getById(musicId);

        if (optionalMusic.isEmpty() || optionalPlaylist.isEmpty()) return false;

        Playlist playlist = optionalPlaylist.get();
        Music music = optionalMusic.get();

        if (music.getPlaylistIds().stream().anyMatch(id -> id.equals(playlistId))) return false;

        ArrayList<Music> musics = playlist.getMusics();
        ArrayList<String> playlistIds = music.getPlaylistIds();

        musics.add(music);
        playlistIds.add(playlistId);

        playlist.setMusics(musics);
        music.setPlaylistIds(playlistIds);

        playlistDao.upsert(playlist);
        musicDao.upsert(music);

        return true;
    }

    @Override
    public boolean removeMusicFromPlaylist(String playlistId, String musicId) {
        Optional<Playlist> optionalPlaylist = playlistDao.getById(playlistId);
        Optional<Music> optionalMusic = musicDao.getById(musicId);

        if (optionalMusic.isEmpty() || optionalPlaylist.isEmpty()) return false;

        Playlist playlist = optionalPlaylist.get();
        Music music = optionalMusic.get();

        ArrayList<Music> musics = playlist.getMusics();
        ArrayList<String> playlistIds = music.getPlaylistIds();

        if (!musics.removeIf(m -> m.equals(music))) return false;
        if (!playlistIds.removeIf(id -> id.equals(playlistId))) return false;

        playlist.setMusics(musics);
        music.setPlaylistIds(playlistIds);

        playlistDao.upsert(playlist);
        musicDao.upsert(music);

        return true;
    }

    @Override
    public List<Playlist> getAll() {
        return playlistDao.getAll();
    }

    @Override
    public Optional<Playlist> getById(String id) {
        return playlistDao.getById(id);
    }

    @Override
    public Playlist save(Playlist element) {
        // The server manage itself the ids of the playlists.
        element.setId(IdGenerator.generateRandomId());

        return playlistDao.upsert(element);
    }

    @Override
    public void deleteById(String id) {
        playlistDao.deleteById(id);
    }

    @Override
    public boolean doesElementExists(String id) {
        return playlistDao.getById(id).isPresent();
    }
}
