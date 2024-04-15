package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.utils.ServiceResult;

/**
 * Service for retrieving covers of musics and other elements if any.
 */
public interface CoverService {
    /**
     * Retrieves the url of a cover from its id.
     *
     * @param id the id of the Cover to retrieve.
     * @return a ServiceResult, holding the found Cover or an error.
     */
    ServiceResult<?> getById(String id);
}
