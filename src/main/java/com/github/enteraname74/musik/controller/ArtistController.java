package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.controller.utils.ControllerUtils;
import com.github.enteraname74.musik.domain.model.ArtistPreview;
import com.github.enteraname74.musik.domain.service.ArtistService;
import com.github.enteraname74.musik.domain.service.AuthService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    private final AuthService authService;
    private final ArtistService artistService;

    @Autowired
    public ArtistController(AuthService authService, ArtistService artistService) {
        this.authService = authService;
        this.artistService = artistService;
    }

    @GetMapping("/{name}")
    ResponseEntity<?> getArtist(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String name
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;

        ServiceResult<?> result = artistService.getByName(name);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

    @GetMapping("/all")
    ResponseEntity<?> getAll(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;

        List<ArtistPreview> artists = artistService.getAll();

        return new ResponseEntity<>(artists, HttpStatus.OK);
    }
}
