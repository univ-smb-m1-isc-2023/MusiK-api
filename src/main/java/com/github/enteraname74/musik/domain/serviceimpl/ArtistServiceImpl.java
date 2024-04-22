package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.domain.model.Artist;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import com.github.enteraname74.musik.domain.service.ArtistService;
import com.github.enteraname74.musik.domain.utils.ServiceMessages;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the ArtistService.
 */
@Service
public class ArtistServiceImpl implements ArtistService {

    private final MusicRepository musicRepository;

    @Autowired
    public ArtistServiceImpl(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public ServiceResult<?> getByName(String name) {
        List<Music> allMusics = musicRepository.getAll();

        String usableName = name.toLowerCase();

        Map<String, List<Music>> artists = allMusics.stream().collect(Collectors.groupingBy(Music::getArtist));

        List<Artist> allArtists = artists.entrySet().stream().map(artistInfo -> new Artist(
                artistInfo.getKey(),
                artistInfo.getValue(),
                artistInfo.getValue().get(0).getAlbumArtworkUrl()
        )).toList();

        Optional<Artist> optionalArtist = allArtists.stream().filter(
                artist ->
                        artist.name().toLowerCase().equals(usableName)
        ).findFirst();

        if (optionalArtist.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.CANNOT_FIND_ARTIST
            );
        } else {
            return new ServiceResult<>(
                    HttpStatus.OK,
                    optionalArtist.get()
            );
        }
    }
}
