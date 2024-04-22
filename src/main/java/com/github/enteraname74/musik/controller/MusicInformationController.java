package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.controller.utils.ControllerUtils;
import com.github.enteraname74.musik.domain.service.AuthService;
import com.github.enteraname74.musik.domain.service.MusicInformationService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing API calls related to musics.
 */
@RestController
@RequestMapping("/music/information")
public class MusicInformationController {
    private final MusicInformationService musicInformationService;
    private final AuthService authService;

    @Autowired
    public MusicInformationController(
            MusicInformationService musicInformationService, AuthService authService
    ) {
        this.musicInformationService = musicInformationService;
        this.authService = authService;
    }

    /**
     * Retrieve all Musics.
     *
     * @return a list containing all Musics or an empty list if there is an error.
     */
    @GetMapping("/all")
    ResponseEntity<?> getAll(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;
        return new ResponseEntity<>(
                musicInformationService.getAll(),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves a Music from its id.
     *
     * @param id the id of the Music to retrieve.
     * @return a ResponseEntity, with the found Music or an error.
     */
    @GetMapping("/{id}")
    ResponseEntity<?> get(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String id
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;
        ServiceResult<?> result = musicInformationService.getById(id);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }


    /**
     * Delete a Music.
     *
     * @param id the id of the Music to delete.
     * @return a ResponseEntity, with the result of the request.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String id
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;
        ServiceResult<?> result = musicInformationService.deleteById(id);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }
}
