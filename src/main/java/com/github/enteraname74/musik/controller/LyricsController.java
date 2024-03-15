package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.domain.service.LyricsService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lyrics")
public class LyricsController {
    private final LyricsService lyricsService;

    @Autowired
    public LyricsController(LyricsService lyricsService) {
        this.lyricsService = lyricsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        ServiceResult<?> result = lyricsService.getLyricsFromMusic(id);

        return new ResponseEntity<>(
                result.getResult(),
                result.getHttpStatus()
        );
    }
}
