package com.github.enteraname74.musik.infrastructure.repositoryimpl;

import com.github.enteraname74.musik.domain.dao.MusicDao;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class MusicRepositoryTests {

    private MusicRepository musicRepository;

    @MockBean
    private MusicDao musicDao;

    ArrayList<Music> allMusics;

    @BeforeEach
    public void init() {
        musicRepository = new PostgresMusicRepositoryImpl(musicDao);

        Music firstMusic = new Music("1", "", "", "", "");
        Music secondMusic = new Music("2", "", "", "", "");


        allMusics = new ArrayList<>(Arrays.asList(firstMusic, secondMusic));

        Mockito.when(musicDao.getAll()).thenReturn(allMusics);
        Mockito.when(musicDao.getById(any(String.class))).thenAnswer(i -> {
            String id = (String) i.getArguments()[0];
            return allMusics.stream().filter(
                    music -> music.getId().equals(id)
            ).findFirst();
        });
        Mockito.when(musicDao.upsert(any(Music.class))).thenAnswer(i -> {
            Music music = (Music) i.getArguments()[0];
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
        }).when(musicDao).deleteById(any(String.class));
    }

    @Test
    public void givenCorrectMusicId_whenGetById_thenFoundMusic() {
        String correctId = "1";
        Optional<Music> foundMusic = musicRepository.getById(correctId);

        Assert.isTrue(foundMusic.isPresent(), "The id should have returned a found music");
    }

    @Test
    public void givenWrongMusicId_whenGetById_thenFoundNothing() {
        String wrongId = "45";
        Optional<Music> foundMusic = musicRepository.getById(wrongId);

        Assert.isTrue(foundMusic.isEmpty(), "The id should have returned nothing");
    }

    @Test
    public void givenMusics_whenGetAll_thenRetrieveAllMusics() {
        List<Music> result = musicRepository.getAll();

        Assert.isTrue(result.size() == 2, "All musics were not retrieved");
    }

    @Test
    public void givenCorrectMusicId_whenDeleteById_thenShouldBeDeleted() {
        musicRepository.deleteById("1");
        Optional<Music> foundMusic = musicRepository.getById("1");

        Assert.isTrue(foundMusic.isEmpty(), "The deleted music should not be found");
    }

    @Test
    public void givenCorrectMusicId_whenCheckingIfExists_thenReturnsTrue() {
        boolean doesExists = musicRepository.doesElementExists("1");

        Assert.isTrue(doesExists, "The music should be found");
    }

    @Test
    public void givenWrongMusicId_whenCheckingIfExists_thenReturnsTrue() {
        boolean doesExists = musicRepository.doesElementExists("45");

        Assert.isTrue(doesExists, "The music should not be found");
    }

    @Test
    public void givenWrongMusicId_whenDeleteById_thenNothingChange() {
        musicRepository.deleteById("45");

        List<Music> allMusics = musicRepository.getAll();

        Assert.isTrue(allMusics.size() == 2, "A music was deleted, it should not be the case");
    }

    @Test
    public void givenNewMusic_whenAddingMusic_thenMusicAddedInAllMusics() {
        Music newMusic = new Music("3", "", "", "", "");
        Music result = musicRepository.save(newMusic);

        Assert.isTrue(newMusic.equals(result), "The element should not be altered after being saved");

        List<Music> allMusics = musicRepository.getAll();
        Assert.isTrue(allMusics.size() == 3, "The music was not added to all musics");
    }
}
