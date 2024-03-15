package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.controller.utils.ControllerMessages;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import com.github.enteraname74.musik.domain.service.LyricsService;
import com.github.enteraname74.musik.domain.utils.AppHttpClient;
import com.github.enteraname74.musik.domain.utils.LyristResult;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Implementation of the LyricsService.
 */
@Service
public class LyricsServiceImpl implements LyricsService {
    private final AppHttpClient client;

    private final MusicRepository musicRepository;

    @Autowired
    public LyricsServiceImpl(AppHttpClient client, MusicRepository musicRepository) {
        this.client = client;
        this.musicRepository = musicRepository;
    }

    @Override
    public ServiceResult<?> getLyricsFromMusic(String musicId) {
        Optional<Music> optionalMusic = musicRepository.getById(musicId);

        if (optionalMusic.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ControllerMessages.WRONG_MUSIC_ID
            );
        }

        Music foundMusic = optionalMusic.get();

        try {
            String initialPath = "https://lyrist.vercel.app/api/"+foundMusic.getName()+"/"+foundMusic.getArtist();
            String encodedPath  = initialPath.replace(" ", "%20");
            System.out.println(encodedPath);
            HttpResponse<String> response = client.doGet(encodedPath);
            Gson gson = new Gson();
            LyristResult lyristResult = gson.fromJson(response.body(), LyristResult.class);

            return new ServiceResult<>(
                    HttpStatus.OK,
                    lyristResult.getLyrics()
            );

        } catch (Exception e) {
            System.out.println("ERROR: "+e);
            return new ServiceResult<>(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    ControllerMessages.SERVICE_UNREACHABLE
            );
        }
    }
}
