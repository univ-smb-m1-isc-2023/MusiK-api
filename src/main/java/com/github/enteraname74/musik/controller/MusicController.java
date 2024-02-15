package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.domain.model.Music;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Controller for managing API calls related to musics.
 */
@RestController
@RequestMapping("/music")
public class MusicController {
    @GetMapping("/all")
    List<Music> getAllMusics() {
        return Collections.emptyList();
    }
}
