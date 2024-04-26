package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.domain.model.Album;
import com.github.enteraname74.musik.domain.model.AlbumPreview;
import com.github.enteraname74.musik.domain.service.AuthService;
import com.github.enteraname74.musik.domain.service.LyricsService;
import com.github.enteraname74.musik.domain.utils.ServiceMessages;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(LyricsController.class)
public class LyricsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LyricsService lyricsService;

    @MockBean
    private AuthService authService;


    @BeforeEach
    public void init() {
        Mockito.when(authService.isUserAuthenticated(any(String.class))).thenAnswer(i -> true);
        Mockito.when(lyricsService.getLyricsFromMusic(any(String.class))).thenAnswer(i -> new ServiceResult<>(HttpStatus.OK, "Lyrics"));
    }

    @Test
    public void givenMusicId_whenRetrievingLyrics_thenShouldReturnLyrics() throws Exception {
        mockMvc.perform(get("/lyrics/{id}", "test").header("Authorization", "AUTH"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Lyrics")));
    }
}
