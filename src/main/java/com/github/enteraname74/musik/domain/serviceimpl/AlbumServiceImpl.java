package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.domain.model.Album;
import com.github.enteraname74.musik.domain.model.ArtistAlbum;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import com.github.enteraname74.musik.domain.service.AlbumService;
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
 * Implementation of the AlbumService
 */
@Service
public class AlbumServiceImpl implements AlbumService {
    private final MusicRepository musicRepository;

    @Autowired
    public AlbumServiceImpl(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public ServiceResult<?> getByNameAndArtist(String name, String artist) {
        List<Music> allMusics = musicRepository.getAll();

        String usableName = name.toLowerCase();
        String usableArtist = artist.toLowerCase();

        Map<ArtistAlbum, List<Music>> albums = allMusics.stream().collect(Collectors.groupingBy(music -> new ArtistAlbum(music.getArtist(), music.getAlbum())));

        List<Album> allAlbums = albums.entrySet().stream().map(albumInfo -> new Album(
                albumInfo.getKey().album(),
                albumInfo.getKey().artist(),
                albumInfo.getValue(),
                albumInfo.getValue().get(0).getAlbumArtworkUrl()
        )).toList();

        Optional<Album> optionalAlbum = allAlbums.stream().filter(
                album ->
                        album.name().toLowerCase().equals(usableName) && album.artist().toLowerCase().equals(usableArtist)
        ).findFirst();

        if (optionalAlbum.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.CANNOT_FIND_ALBUM
            );
        } else {
            return new ServiceResult<>(
                    HttpStatus.OK,
                    optionalAlbum.get()
            );
        }
    }
}
