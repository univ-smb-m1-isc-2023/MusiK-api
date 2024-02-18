package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.domain.service.MusicFileService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/music/file")
public class MusicFileController {
    private final MusicFileService musicFileService;

    @Autowired
    public MusicFileController(MusicFileService musicFileService) {
        this.musicFileService = musicFileService;
    }

    /**
     * Tries to save a music file to the music folder.
     *
     * @param file the file to save, as a Multipart file in order to better manage big files.
     * @return a ResponseEntity, with the result of the request.
     */
    @PostMapping("/upload")
    public ResponseEntity<?> save(@RequestParam("file") MultipartFile file) {
        ServiceResult<?> result = musicFileService.save(file);

        return new ResponseEntity<>(
                result.getResult(),
                result.getHttpStatus()
        );
    }
}
