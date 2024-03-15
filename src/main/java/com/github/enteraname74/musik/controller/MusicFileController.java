package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.domain.service.MusicFileService;
import com.github.enteraname74.musik.domain.utils.FileUtils;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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

    /**
     * Tries to retrieve a music file from its id. (the id of the music file is included in its name).
     *
     * @param id the id of the music file to retrieve.
     * @return a ResponseEntity, with the found file or an error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        System.out.println("Got request !");
        ServiceResult<?> result = musicFileService.getById(id);

        if (result.getHttpStatus().is2xxSuccessful()) {
            System.out.println("RESULT: "+result.getResult());
            // Got a file !
            File file = (File) result.getResult(); // Replace with the path to your file

            // Set the content type and length of the file
            HttpHeaders headers = new HttpHeaders();
            String contentType = FileUtils.getContentType(file.getName());
            headers.setContentType(MediaType.parseMediaType(contentType));
            System.out.println("CONTENT TYPE OF FILE: "+contentType);
            headers.setContentLength(file.length());
            headers.setContentDispositionFormData("attachment", file.getName());

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new FileSystemResource(file));
        }

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }
}
