package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.domain.model.Playlist;
import com.github.enteraname74.musik.domain.service.PlaylistService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {
    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping()
    public ResponseEntity<?> add(
            @RequestBody Playlist playlist
    ) {
        ServiceResult<?> result =  playlistService.save(playlist);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

    /**
     * Retrieve all Playlists.
     *
     * @return a list containing all Playlists or an empty list if there is an error.
     */
    @GetMapping("/all")
    List<Playlist> getAll() { return playlistService.getAll(); }

    /**
     * Retrieves a Music from its id.
     *
     * @param id the id of the Music to retrieve.
     * @return a ResponseEntity, with the found Music or an error.
     */
    @GetMapping("/{id}")
    ResponseEntity<?> get(
            @PathVariable String id
    ) {
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
            @PathVariable String id
    ) {
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
            @PathVariable String playlistId,
            @PathVariable String musicId
    ) {
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
            @PathVariable String playlistId,
            @PathVariable String musicId
    ) {
        ServiceResult<?> result = playlistService.removeMusicToPlaylist(playlistId, musicId);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }
}
