package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.controller.utils.ControllerUtils;
import com.github.enteraname74.musik.domain.model.Playlist;
import com.github.enteraname74.musik.domain.service.AuthService;
import com.github.enteraname74.musik.domain.service.PlaylistService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    private final PlaylistService playlistService;
    private final AuthService authService;

    @Autowired
    public PlaylistController(PlaylistService playlistService, AuthService authService) {
        this.playlistService = playlistService;
        this.authService = authService;
    }

    /**
     * Create a playlist.
     *
     * @param playlist the playlist to create.
     * @return a ResponseEntity, with the response of the request.
     */
    @PostMapping()
    public ResponseEntity<?> add(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @RequestBody Playlist playlist
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;
        ServiceResult<?> result =  playlistService.save(playlist);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

    /**
     * Retrieve all Playlists.
     *
     * @return a list containing all Playlists or an empty list if there is an error.
     */
    @GetMapping("/all")
    ResponseEntity<?> getAll(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;

        return new ResponseEntity<>(
                playlistService.getAll(),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves a Playlist from its id.
     *
     * @param id the id of the Playlist to retrieve.
     * @return a ResponseEntity, with the found Playlist or an error.
     */
    @GetMapping("/{id}")
    ResponseEntity<?> get(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String id
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;

        ServiceResult<?> result = playlistService.getById(id);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }


    /**
     * Delete a Playlist.
     *
     * @param id the id of the Playlist to delete.
     * @return a ResponseEntity, with the result of the request.
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String id
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;
        ServiceResult<?> result = playlistService.deleteById(id);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

    /**
     * Add a Music to a Playlist.
     *
     * @param playlistId the id of the concerned Playlist.
     * @param musicId The id of the Music to add to the given Playlist.
     * @return a ResponseEntity, with the result of the request.
     */
    @GetMapping("/add/{playlistId}/{musicId}")
    ResponseEntity<?> addMusicToPlaylist(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String playlistId,
            @PathVariable String musicId
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;
        ServiceResult<?> result = playlistService.addMusicToPlaylist(playlistId, musicId);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

    /**
     * Remove a Music from a Playlist.
     *
     * @param playlistId the id of the concerned Playlist.
     * @param musicId The id of the Music to remove from the given Playlist.
     * @return a ResponseEntity, with the result of the request.
     */
    @GetMapping("/remove/{playlistId}/{musicId}")
    ResponseEntity<?> removeMusicFromPlaylist(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable String playlistId,
            @PathVariable String musicId
    ) {
        if (!authService.isUserAuthenticated(token)) return ControllerUtils.UNAUTHORIZED_RESPONSE;
        ServiceResult<?> result = playlistService.removeMusicToPlaylist(playlistId, musicId);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }
}
