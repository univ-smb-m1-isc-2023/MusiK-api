package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.controller.utils.ControllerUtils;
import com.github.enteraname74.musik.domain.service.AuthService;
import com.github.enteraname74.musik.domain.service.LyricsService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lyrics")
public class LyricsController {
    private final LyricsService lyricsService;
    private final AuthService authService;

    @Autowired
    public LyricsController(LyricsService lyricsService, AuthService authService) {
        this.lyricsService = lyricsService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String id
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;
        ServiceResult<?> result = lyricsService.getLyricsFromMusic(id);

        return new ResponseEntity<>(
                result.getResult(),
                result.getHttpStatus()
        );
    }
}
