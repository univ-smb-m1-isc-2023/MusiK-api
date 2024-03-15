package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.service.MusicInformationService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing API calls related to musics.
 */
@RestController
@RequestMapping("/music/information")
public class MusicInformationController {
    private final MusicInformationService musicInformationService;

    @Autowired
    public MusicInformationController(
            MusicInformationService musicInformationService
    ) {
        this.musicInformationService = musicInformationService;
    }

    /**
     * Retrieve all Musics.
     *
     * @return a list containing all Musics or an empty list if there is an error.
     */
    @GetMapping("/all")
    List<Music> getAll() {
        return musicInformationService.getAll();
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
        ServiceResult<?> result = musicInformationService.getById(id);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(
            @PathVariable String id
    ) {
        ServiceResult<?> result = musicInformationService.deleteById(id);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }
}
