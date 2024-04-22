package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.model.AlbumPreview;
import com.github.enteraname74.musik.domain.model.ArtistPreview;
import com.github.enteraname74.musik.domain.model.Music;

import java.util.List;

/**
 * Service used to manage search.
 */
public interface SearchService {

    /**
     * Retrieve musics from a given search string.
     *
     * @param search the search used for retrieving musics based on their name, album and artist.
     * @return a list of musics matching the search
     */
    List<Music> getMusicsFromSearch(String search);

    /**
     * Retrieve albums from a given search string.
     *
     * @param search the search used for retrieving albums based on their name and artist.
     * @return a list of albums matching the search
     */
    List<AlbumPreview> getAlbumsFromSearch(String search);

    /**
     * Retrieve artists from a given search string.
     *
     * @param search the search used for retrieving artists based on their name.
     * @return a list of artists matching the search
     */
    List<ArtistPreview> getArtistsFromSearch(String search);
}
