package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.model.AlbumPreview;
import com.github.enteraname74.musik.domain.utils.ServiceResult;

import java.util.List;

/**
 * Service for managing albums.
 */
public interface AlbumService {

    /**
     * Retrieve an Album by its name.
     *
     * @param name the name of the album to retrieve.
     * @param artist the name of the artist of the album.
     * @return a ServiceResult, indicating the result of the request.
     */
    ServiceResult<?> getByNameAndArtist(String name, String artist);

    /**
     * Retrieve all albums.
     *
     * @return a list containing all albums as preview.
     */
    List<AlbumPreview> getAll();
}