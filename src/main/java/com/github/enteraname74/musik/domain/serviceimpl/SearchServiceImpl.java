package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.domain.model.AlbumPreview;
import com.github.enteraname74.musik.domain.model.ArtistAlbum;
import com.github.enteraname74.musik.domain.model.ArtistPreview;
import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.repository.MusicRepository;
import com.github.enteraname74.musik.domain.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the SearchService.
 */
@Service
public class SearchServiceImpl implements SearchService {
    private final MusicRepository musicRepository;

    @Autowired
    public SearchServiceImpl(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public List<Music> getMusicsFromSearch(String search) {
        List<Music> allMusics = musicRepository.getAll();
        String usableSearch = search.toLowerCase();

        return allMusics.stream().filter(music ->
                music.getName().toLowerCase().contains(usableSearch)
                        || music.getAlbum().toLowerCase().contains(usableSearch)
                        || music.getArtist().toLowerCase().contains(usableSearch)
        ).toList();
    }

    @Override
    public List<AlbumPreview> getAlbumsFromSearch(String search) {
        List<Music> allMusics = musicRepository.getAll();
        String usableSearch = search.toLowerCase();

        Map<ArtistAlbum, List<Music>> albums = allMusics.stream().collect(Collectors.groupingBy(music -> new ArtistAlbum(music.getArtist(), music.getAlbum())));

        List<AlbumPreview> albumPreviews = albums.entrySet().stream().map(albumInfo -> new AlbumPreview(
                albumInfo.getKey().album(),
                albumInfo.getKey().artist(),
                albumInfo.getValue().size(),
                albumInfo.getValue().get(0).getAlbumArtworkUrl()
        )).toList();

        return albumPreviews.stream().filter(albumPreview ->
                albumPreview.albumName().toLowerCase().contains(usableSearch)
                        || albumPreview.artistName().toLowerCase().contains(usableSearch)
        ).toList();
    }

    @Override
    public List<ArtistPreview> getArtistsFromSearch(String search) {
        List<Music> allMusics = musicRepository.getAll();
        String usableSearch = search.toLowerCase();

        Map<String, List<Music>> artists = allMusics.stream().collect(Collectors.groupingBy(Music::getArtist));

        List<ArtistPreview> artistPreviews = artists.entrySet().stream().map(artistInfo -> new ArtistPreview(
                artistInfo.getKey(),
                artistInfo.getValue().size(),
                artistInfo.getValue().get(0).getAlbumArtworkUrl()
        )).toList();

        return artistPreviews.stream().filter(artistPreview ->
                artistPreview.name().toLowerCase().contains(usableSearch)
        ).toList();
    }
}
