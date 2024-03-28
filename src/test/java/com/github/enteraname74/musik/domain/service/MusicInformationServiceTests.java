package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.handler.MusicFilePersistenceManager;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import com.github.enteraname74.musik.domain.repository.PlaylistRepository;
import com.github.enteraname74.musik.domain.serviceimpl.MusicInformationServiceImpl;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SuppressWarnings("unchecked")
public class MusicInformationServiceTests {
    private MusicInformationService musicInformationService;

    @MockBean
    private MusicRepository musicRepository;

    @MockBean
    private PlaylistRepository playlistRepository;

    @MockBean
    private MusicFilePersistenceManager musicFilePersistenceManager;
    ArrayList<Music> allMusics;

    @BeforeEach
    public void init() {
        musicInformationService = new MusicInformationServiceImpl(musicRepository, playlistRepository, musicFilePersistenceManager);
        Music firstMusic = new Music("1", "", "", "", "", new ArrayList<>());
        Music secondMusic = new Music("2", "", "", "", "", new ArrayList<>());

        allMusics = new ArrayList<>(Arrays.asList(firstMusic, secondMusic));

        Mockito.when(musicRepository.doesElementExists(any(String.class))).thenAnswer(i ->
                {
                    String id = (String) i.getArguments()[0];
                    return allMusics.stream().anyMatch(
                            music -> music.getId().equals(id)
                    );
                }
        );
        Mockito.when(musicRepository.getById(any(String.class))).thenAnswer(i -> {
                    String id = (String) i.getArguments()[0];
                    return allMusics.stream().filter(
                            music -> music.getId().equals(id)
                    ).findFirst();
                }
        );
        Mockito.when(musicRepository.getAll()).thenReturn(allMusics);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length == 1 && arguments[0] != null) {
                String id = (String) arguments[0];
                allMusics = new ArrayList<>(allMusics.stream().filter(music -> !music.getId().equals(id)).toList());
                System.out.println("SIZE: " + allMusics.size());
            }
            return null;
        }).when(musicRepository).deleteById(any(String.class));

        Mockito.when(musicRepository.save(any())).thenAnswer(i -> {
            Music music = (Music) i.getArguments()[0];
            allMusics.add(music);
            return music;
        });

        Mockito.when(playlistRepository.removeMusicFromPlaylist(any(String.class), any(String.class))).thenAnswer(i -> true);
        Mockito.doAnswer(invocationOnMock -> true).when(musicFilePersistenceManager).deleteFile(any(String.class));
    }

    @Test
    public void givenMusics_whenGetById_thenFoundMusic() {
        String id = "1";
        ServiceResult<?> result = musicInformationService.getById(id);

        Assert.isInstanceOf(Optional.class, result.getResult());
        Assert.isTrue(result.getHttpStatus().equals(HttpStatus.OK), "The returned HTTP status is not correct");
        Optional<Music> foundMusic = (Optional<Music>) result.getResult();
        Assert.isTrue(foundMusic.isPresent(), "No Music was found");
        Assert.isTrue(foundMusic.get().getId().equals(id), "Returned music is not corresponding to the given id");
    }

    @Test
    public void givenMusics_whenGetAll_thenRetrieveAllMusics() {
        List<Music> result = musicInformationService.getAll();

        Assert.isTrue(result.size() == 2, "All musics were not retrieved");
    }

    @Test
    public void givenMusics_whenDeleteById_thenMusicShouldBeRemoved() {
        ServiceResult<?> result = musicInformationService.deleteById("1");
        Assert.isTrue(result.getHttpStatus().equals(HttpStatus.OK), "The music was not deleted correctly");

//        List<Music> allRemainingMusics = musicService.getAll();
//        Assert.isTrue(allRemainingMusics.size() == 1, "The size of the remaining musics is not correct");
//
//        ServiceResult<?> notFoundMusic = musicService.getById("1");
//        Assert.isInstanceOf(String.class, notFoundMusic.getResult());
//        Assert.isTrue(result.getHttpStatus().equals(HttpStatus.NOT_FOUND), "The returned HTTP status is not correct");
    }

    @Test
    public void givenNewMusic_whenAddingMusic_thenMusicAddedInAllMusics() {
        Music newMusic = new Music("3", "", "", "", "", new ArrayList<>());
        ServiceResult<?> result = musicInformationService.save(newMusic);

        Assert.isTrue(result.getHttpStatus().equals(HttpStatus.ACCEPTED), "Music was not added successfully");

        List<Music> allMusics = musicInformationService.getAll();
        Assert.isTrue(allMusics.size() == 3, "The music was not added to all musics");
    }
}
