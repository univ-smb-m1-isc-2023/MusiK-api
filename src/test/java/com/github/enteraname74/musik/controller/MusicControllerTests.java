package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.controller.utils.ControllerMessages;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.service.MusicInformationService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(MusicInformationController.class)
public class MusicControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicInformationService musicInformationService;

    ArrayList<Music> allMusics;

    @BeforeEach
    public void init() {
        Music firstMusic = new Music("1", "MUSICNAME", "", "", "", new ArrayList<>());
        Music secondMusic = new Music("2", "", "", "", "", new ArrayList<>());

        allMusics = new ArrayList<>(Arrays.asList(firstMusic, secondMusic));

        Mockito.when(musicInformationService.getById(firstMusic.getId())).thenAnswer(i -> {
            String id = (String) i.getArguments()[0];
            Optional<Music> foundMusic = allMusics.stream().filter(
                    music -> music.getId().equals(id)
            ).findFirst();

            if (foundMusic.isPresent()) {
                return new ServiceResult<>(
                        HttpStatus.OK,
                        foundMusic.get()
                );
            } else {
                return new ServiceResult<>(
                        HttpStatus.NOT_FOUND,
                        ControllerMessages.WRONG_MUSIC_ID
                );
            }
        });
        Mockito.when(musicInformationService.getAll()).thenReturn(allMusics);

        Mockito.doAnswer(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length == 1 && arguments[0] != null) {
                String id = (String) arguments[0];
                allMusics = new ArrayList<>(allMusics.stream().filter(music -> !music.getId().equals(id)).toList());
                System.out.println("SIZE: " + allMusics.size());
            }
            return new ServiceResult<>(HttpStatus.OK, ControllerMessages.MUSIC_DELETED);
        }).when(musicInformationService).deleteById(any(String.class));

        Mockito.when(musicInformationService.save(any())).thenAnswer(i -> {
            Music music = (Music) i.getArguments()[0];
            allMusics.add(music);
            return new ServiceResult<>(HttpStatus.ACCEPTED, music);
        });
    }

    @Test
    public void givenMusics_whenGetById_thenRetrieveMusic() throws Exception {
        mockMvc.perform(get("/music/information/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MUSICNAME"));
    }

    @Test
    public void givenMusics_whenGetAll_thenShouldGetAllMusics() throws Exception {
        mockMvc.perform(get("/music/information/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void givenMusics_whenDeleteById_thenMusicShouldBeDeleted() throws Exception {
        mockMvc.perform(delete("/music/information/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ControllerMessages.MUSIC_DELETED)));
    }
}
