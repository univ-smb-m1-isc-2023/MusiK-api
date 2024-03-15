package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service used to manage music file.
 */
public interface MusicFileService {

    /**
     * Save a multipart file to the folder used by the system to save music files.
     *
     * @param file the file to save.
     * @return a ServiceResult, indicating the result of the request.
     */
    ServiceResult<?> save(MultipartFile file);

    /**
     * Tries to retrieve a music file by its id.
     * The id of the music file is also its name in the folder where the file is saved.
     *
     * @param id the id of the music file.
     * @return a ServiceResult, with the file or an error if the file was not found.
     */
    ServiceResult<?> getById(String id);
}
