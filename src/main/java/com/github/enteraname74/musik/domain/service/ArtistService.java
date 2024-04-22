package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.utils.ServiceResult;

/**
 * Service for managing artists.
 */
public interface ArtistService {

    /**
     * Retrieve an Artist by its name.
     *
     * @param name the name of the artist to retrieve.
     * @return a ServiceResult, indicating the result of the request.
     */
    ServiceResult<?> getByName(String name);
}
