package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.controller.utils.ControllerUtils;
import com.github.enteraname74.musik.domain.model.AlbumPreview;
import com.github.enteraname74.musik.domain.model.ArtistPreview;
import com.github.enteraname74.musik.domain.service.AlbumService;
import com.github.enteraname74.musik.domain.service.AuthService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final AuthService authService;
    private final AlbumService albumService;

    @Autowired
    public AlbumController(AuthService authService, AlbumService albumService) {
        this.authService = authService;
        this.albumService = albumService;
    }

    @GetMapping("/{name}/{artist}")
    ResponseEntity<?> getAlbum(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String name,
            @PathVariable String artist
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;

        ServiceResult<?> result = albumService.getByNameAndArtist(name, artist);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

    @GetMapping("/all")
    ResponseEntity<?> getAll(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;

        List<AlbumPreview> albums = albumService.getAll();

        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
}
