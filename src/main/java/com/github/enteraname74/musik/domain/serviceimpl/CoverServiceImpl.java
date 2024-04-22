package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.domain.utils.ServiceMessages;
import com.github.enteraname74.musik.domain.handler.MusicCoverRetriever;
import com.github.enteraname74.musik.domain.service.CoverService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the CoverService.
 */
@Service
public class CoverServiceImpl implements CoverService {

    private final MusicCoverRetriever musicCoverRetriever;

    @Autowired
    public CoverServiceImpl(MusicCoverRetriever musicCoverRetriever) {
        this.musicCoverRetriever = musicCoverRetriever;
    }

    @Override
    public ServiceResult<?> getById(String id) {
        Optional<byte[]> coverData = musicCoverRetriever.getById(id);

        if (coverData.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.CANNOT_FIND_COVER
            );
        } else {
            return new ServiceResult<>(
                    HttpStatus.OK,
                    coverData.get()
            );
        }
    }
}
