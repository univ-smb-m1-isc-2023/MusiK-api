package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.controller.utils.ControllerUtils;
import com.github.enteraname74.musik.domain.service.AuthService;
import com.github.enteraname74.musik.domain.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final AuthService authService;
    private final SearchService searchService;

    @Autowired
    public SearchController(AuthService authService, SearchService searchService) {
        this.authService = authService;
        this.searchService = searchService;
    }

    @GetMapping("/musics/{search}")
    ResponseEntity<?> getMusicsFromSearch(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String search
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;

        return new ResponseEntity<>(searchService.getMusicsFromSearch(search), HttpStatus.OK);
    }

    @GetMapping("/albums/{search}")
    ResponseEntity<?> getAlbumsFromSearch(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String search
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;

        return new ResponseEntity<>(searchService.getAlbumsFromSearch(search), HttpStatus.OK);
    }

    @GetMapping("/artists/{search}")
    ResponseEntity<?> getArtistsFromSearch(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String search
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;

        return new ResponseEntity<>(searchService.getArtistsFromSearch(search), HttpStatus.OK);
    }
}
