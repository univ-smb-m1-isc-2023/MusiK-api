package com.github.enteraname74.musik.infrastructure.daoimpl;

import com.github.enteraname74.musik.domain.dao.PlaylistDao;
import com.github.enteraname74.musik.domain.model.Playlist;
import com.github.enteraname74.musik.infrastructure.jpa.PostgresMusicJpa;
import com.github.enteraname74.musik.infrastructure.jpa.PostgresPlaylistJpa;
import com.github.enteraname74.musik.infrastructure.model.PostgresMusicEntity;
import com.github.enteraname74.musik.infrastructure.model.PostgresPlaylistEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class PlaylistDaoTests {

    private PlaylistDao playlistDao;

    @MockBean
    private PostgresMusicJpa musicJpa;

    @MockBean
    private PostgresPlaylistJpa playlistJpa;

    ArrayList<PostgresMusicEntity> allMusics;
    ArrayList<PostgresPlaylistEntity> allPlaylists;
    
    public void initMusicJpa() {
        Mockito.when(musicJpa.findAll()).thenReturn(allMusics);
        Mockito.when(musicJpa.findById(any(String.class))).thenAnswer(i -> {
            String id = (String) i.getArguments()[0];
            return allMusics.stream().filter(
                    music -> music.getId().equals(id)
            ).findFirst();
        });
        Mockito.when(musicJpa.save(any(PostgresMusicEntity.class))).thenAnswer(i -> {
            PostgresMusicEntity music = (PostgresMusicEntity) i.getArguments()[0];
            allMusics.add(music);
            return music;
        });
        Mockito.doAnswer(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length == 1 && arguments[0] != null) {
                String id = (String) arguments[0];
                allMusics = new ArrayList<>(allMusics.stream().filter(music -> !music.getId().equals(id)).toList());
                System.out.println("SIZE: " + allMusics.size());
            }
            return null;
        }).when(musicJpa).deleteById(any(String.class));
    }
    
    private void initPlaylistJpa() {
        Mockito.when(playlistJpa.findAll()).thenReturn(allPlaylists);
        Mockito.when(playlistJpa.findById(any(String.class))).thenAnswer(i -> {
            String id = (String) i.getArguments()[0];
            return allPlaylists.stream().filter(
                    playlist -> playlist.getId().equals(id)
            ).findFirst();
        });
        Mockito.when(playlistJpa.save(any(PostgresPlaylistEntity.class))).thenAnswer(i -> {
            PostgresPlaylistEntity playlist = (PostgresPlaylistEntity) i.getArguments()[0];
            allPlaylists.add(playlist);
            return playlist;
        });
        Mockito.doAnswer(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length == 1 && arguments[0] != null) {
                String id = (String) arguments[0];
                allPlaylists = new ArrayList<>(allPlaylists.stream().filter(playlist -> !playlist.getId().equals(id)).toList());
            }
            return null;
        }).when(playlistJpa).deleteById(any(String.class));
    }

    @BeforeEach
    public void init() {
        playlistDao = new PostgresPlaylistDaoImpl(playlistJpa, musicJpa);

        PostgresMusicEntity firstMusic = new PostgresMusicEntity("1", "", "", "", "", new ArrayList<>());
        PostgresMusicEntity secondMusic = new PostgresMusicEntity("2", "", "", "", "", new ArrayList<>());

        PostgresPlaylistEntity firstPlaylist = new PostgresPlaylistEntity("1", "test", Collections.emptyList());
        PostgresPlaylistEntity secondPlaylist = new PostgresPlaylistEntity("2", "with musics", new ArrayList<>(List.of(firstMusic)));
        
        allMusics = new ArrayList<>(Arrays.asList(firstMusic, secondMusic));
        allPlaylists = new ArrayList<>(Arrays.asList(firstPlaylist, secondPlaylist));

        initPlaylistJpa();
        initMusicJpa();
    }

    @Test
    public void givenCorrectPlaylistId_whenGetById_thenFoundPlaylist() {
        String correctId = "1";
        Optional<Playlist> foundPlaylist = playlistDao.getById(correctId);

        Assert.isTrue(foundPlaylist.isPresent(), "The id should have returned a found playlist");
    }

    @Test
    public void givenWrongPlaylistId_whenGetById_thenFoundNothing() {
        String wrongId = "45";
        Optional<Playlist> foundPlaylist = playlistDao.getById(wrongId);

        Assert.isTrue(foundPlaylist.isEmpty(), "The id should have returned nothing");
    }

    @Test
    public void givenPlaylists_whenGetAll_thenRetrieveAllPlaylists() {
        List<Playlist> result = playlistDao.getAll();

        Assert.isTrue(result.size() == 2, "All playlists were not retrieved");
    }

    @Test
    public void givenCorrectPlaylistId_whenDeleteById_thenShouldBeDeleted() {
        playlistDao.deleteById("1");
        Optional<Playlist> foundPlaylist = playlistDao.getById("1");

        Assert.isTrue(foundPlaylist.isEmpty(), "The deleted playlist should not be found");
    }

    @Test
    public void givenWrongPlaylistId_whenDeleteById_thenNothingChange() {
        playlistDao.deleteById("45");

        List<Playlist> allPlaylists = playlistDao.getAll();

        Assert.isTrue(allPlaylists.size() == 2, "A playlist was deleted, it should not be the case");
    }

    @Test
    public void givenNewPlaylist_whenAddingPlaylist_thenPlaylistAddedInAllPlaylists() {
        Playlist newPlaylist = new Playlist("3", "new playlist", new ArrayList<>());
        Playlist result = playlistDao.upsert(newPlaylist);

        Assert.isTrue(newPlaylist.equals(result), "The element should not be altered after being saved");

        List<Playlist> allPlaylists = playlistDao.getAll();
        Assert.isTrue(allPlaylists.size() == 3, "The playlist was not added to all playlists");
    }
}