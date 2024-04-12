package com.github.enteraname74.musik.infrastructure.daoimpl;

import com.github.enteraname74.musik.domain.dao.MusicDao;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.infrastructure.jpa.PostgresMusicJpa;
import com.github.enteraname74.musik.infrastructure.jpa.PostgresPlaylistJpa;
import com.github.enteraname74.musik.infrastructure.model.PostgresMusicEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class MusicDaoTests {

    private MusicDao musicDao;

    @MockBean
    private PostgresMusicJpa jpa;

    @MockBean
    private PostgresPlaylistJpa playlistJpa;

    ArrayList<PostgresMusicEntity> allMusics;

    @BeforeEach
    public void init() {
        musicDao = new PostgresMusicDaoImpl(jpa, playlistJpa);

        PostgresMusicEntity firstMusic = new PostgresMusicEntity("1", "", "", "", "", Collections.emptyList());
        PostgresMusicEntity secondMusic = new PostgresMusicEntity("2", "", "", "", "", Collections.emptyList());


        allMusics = new ArrayList<>(Arrays.asList(firstMusic, secondMusic));

        Mockito.when(jpa.findAll()).thenReturn(allMusics);
        Mockito.when(jpa.findById(any(String.class))).thenAnswer(i -> {
            String id = (String) i.getArguments()[0];
            return allMusics.stream().filter(
                    music -> music.getId().equals(id)
            ).findFirst();
        });
        Mockito.when(jpa.save(any(PostgresMusicEntity.class))).thenAnswer(i -> {
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
        }).when(jpa).deleteById(any(String.class));
    }

    @Test
    public void givenCorrectMusicId_whenGetById_thenFoundMusic() {
        String correctId = "1";
        Optional<Music> foundMusic = musicDao.getById(correctId);

        Assert.isTrue(foundMusic.isPresent(), "The id should have returned a found music");
    }

    @Test
    public void givenWrongMusicId_whenGetById_thenFoundNothing() {
        String wrongId = "45";
        Optional<Music> foundMusic = musicDao.getById(wrongId);

        Assert.isTrue(foundMusic.isEmpty(), "The id should have returned nothing");
    }

    @Test
    public void givenMusics_whenGetAll_thenRetrieveAllMusics() {
        List<Music> result = musicDao.getAll();

        Assert.isTrue(result.size() == 2, "All musics were not retrieved");
    }

    @Test
    public void givenCorrectMusicId_whenDeleteById_thenShouldBeDeleted() {
        musicDao.deleteById("1");
        Optional<Music> foundMusic = musicDao.getById("1");

        Assert.isTrue(foundMusic.isEmpty(), "The deleted music should not be found");
    }

    @Test
    public void givenWrongMusicId_whenDeleteById_thenNothingChange() {
        musicDao.deleteById("45");

        List<Music> allMusics = musicDao.getAll();

        Assert.isTrue(allMusics.size() == 2, "A music was deleted, it should not be the case");
    }

    @Test
    public void givenNewMusic_whenAddingMusic_thenMusicAddedInAllMusics() {
        Music newMusic = new Music("3", "", "", "", "");
        Music result = musicDao.upsert(newMusic);

        Assert.isTrue(newMusic.equals(result), "The element should not be altered after being saved");

        List<Music> allMusics = musicDao.getAll();
        Assert.isTrue(allMusics.size() == 3, "The music was not added to all musics");
    }
}
