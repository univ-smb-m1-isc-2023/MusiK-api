package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.service.MusicService;
import com.github.enteraname74.musik.domain.utils.IdGenerator;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing API calls related to musics.
 */
@RestController
@RequestMapping("/music")
public class MusicController {
    private final MusicService musicService;

    @Autowired
    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    /**
     * Retrieve all Musics.
     *
     * @return a list containing all Musics or an empty list if there is an error.
     */
    @GetMapping("/all")
    List<Music> getAll() {
        return musicService.getAll();
    }

    /**
     * Retrieves a Music from its id.
     *
     * @param id the id of the Music to retrieve.
     * @return a ResponseEntity, with the found Music or an error.
     */
    @GetMapping("/{id}")
    ResponseEntity<?> get(
            @PathVariable String id
    ) {
        ServiceResult<?> result = musicService.getFromId(id);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(
            @PathVariable String id
    ) {
        ServiceResult<?> result = musicService.delete(id);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

    @GetMapping("/dummy")
    ResponseEntity<?> saveDummy() {
        Music music = new Music(
                IdGenerator.generateRandomId(),
                "",
                "NAME",
                "ARTIST",
                "ALBUM"
        );
        ServiceResult<?> result = musicService.save(music);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }
}
