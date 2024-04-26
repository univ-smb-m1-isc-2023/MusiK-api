package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.domain.model.Album;
import com.github.enteraname74.musik.domain.model.AlbumPreview;
import com.github.enteraname74.musik.domain.service.AlbumService;
import com.github.enteraname74.musik.domain.service.AuthService;
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
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumService albumService;

    @MockBean
    private AuthService authService;

    ArrayList<AlbumPreview> allAlbums;

    @BeforeEach
    public void init() {
        AlbumPreview firstAlbum = new AlbumPreview("", "", 0, "");
        AlbumPreview secondAlbum = new AlbumPreview("test", "test", 0, "");

        Album album = new Album("test", "test", Collections.emptyList(), "");

        allAlbums = new ArrayList<>(Arrays.asList(firstAlbum, secondAlbum));

        Mockito.when(authService.isUserAuthenticated(any(String.class))).thenAnswer(i -> true);

        Mockito.when(albumService.getAll()).thenReturn(allAlbums);
        Mockito.when(albumService.getByNameAndArtist(any(String.class), any(String.class))).thenAnswer(i -> new ServiceResult<>(HttpStatus.OK, album));
    }

    @Test
    public void givenAlbums_whenGetByNameAndArtist_thenShouldReturnFoundAlbum() throws Exception {
        mockMvc.perform(get("/album/{name}/{artist}", "test", "test").header("Authorization", "AUTH"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"));
    }

    @Test
    public void givenAlbums_whenGetAll_thenShouldReturnAllAlbums() throws Exception {
        mockMvc.perform(get("/album/all").header("Authorization", "AUTH"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
