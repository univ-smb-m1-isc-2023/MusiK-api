package com.github.enteraname74.musik.domain.utils;

import com.github.enteraname74.musik.domain.model.MusicMetadata;
import com.github.enteraname74.musik.domain.model.acoustid.AcoustidLookupRequestResult;
import com.github.enteraname74.musik.domain.model.acoustid.AcoustidMatch;
import com.github.enteraname74.musik.domain.model.acoustid.AcoustidRecording;
import com.github.enteraname74.musik.domain.model.acoustid.AcoustidReleaseGroup;

import java.util.Optional;

/**
 * Used to analyze the result of a lookup request on the Acoustid API.
 */
public class AcoustidResultAnalyzer {

    /**
     * Represent the initial metadata of the music file.
     * it will be used if not useful data can be retrieved from the Acoustid API request.
     */
    private final MusicMetadata initialMetadata;

    private final AcoustidLookupRequestResult requestResult;

    public AcoustidResultAnalyzer(
            AcoustidLookupRequestResult requestResult,
            MusicMetadata initialMetadata
    ) {
        this.requestResult = requestResult;
        this.initialMetadata = initialMetadata;
    }

    /**
     * Retrieve useful music information from the result of a lookup request.
     *
     * @return the music information of a request. It can return an empty Music element (beside its id) if
     * no information can be retrieved from the result of the request.
     */
    public MusicMetadata getMusicMetadataFromRequest() {
        Optional<AcoustidMatch> optimalMatch = getOptimalMatch();
        if (optimalMatch.isEmpty()) return initialMetadata;
        System.out.println("Got optimal match...");

        Optional<AcoustidRecording> optionalRecording = getOptimalRecording(optimalMatch.get());

        if (optionalRecording.isEmpty()) return initialMetadata;
        System.out.println("Got optimal recording...");
        AcoustidRecording optimalRecording = optionalRecording.get();

        return new MusicMetadata(
                getMusicTitle(optimalRecording),
                getArtistName(optimalRecording),
                getAlbumName(optimalRecording)
        );
    }

    /**
     * Retrieve the music title from a recording.
     *
     * @param recording the recording containing the music title.
     * @return the title of the music. If nothing was found, returns the title from the initial metadata.
     */
    private String getMusicTitle(AcoustidRecording recording) {
        if (recording.getTitle() == null) return initialMetadata.getName();
        else return recording.getTitle();
    }

    /**
     * Retrieve the album name of a recording.
     *
     * @param recording the recording containing the album name.
     * @return the name of the album. If nothing was found, returns the album from the initial metadata.
     */
    private String getAlbumName(AcoustidRecording recording) {
        if (recording.getReleaseGroups() == null) return initialMetadata.getAlbum();
        if (recording.getReleaseGroups().isEmpty()) return initialMetadata.getAlbum();

        return recording.getReleaseGroups().stream().filter(
                // We try to find an album matching with the initial metadata.
                album -> album.getTitle().equals(initialMetadata.getAlbum())
        ).findFirst().orElse(
                // Else, we get the first album.
                recording.getReleaseGroups().get(0)
        ).getTitle();
    }

    /**
     * Retrieve the artist name of a recording.
     *
     * @param recording the recording containing the artist name.
     * @return the name of the artist. If nothing was found, returns the artist from the initial metadata.
     */
    private String getArtistName(AcoustidRecording recording) {
        if (recording.getArtists() == null) return initialMetadata.getArtist();
        if (recording.getArtists().isEmpty()) return initialMetadata.getArtist();

        return recording.getArtists().stream().filter(
                // We try to find an artist matching with the initial metadata.
                artist -> artist.getName().equals(initialMetadata.getArtist())
        ).findFirst().orElse(
                // Else, we get the first artist.
                recording.getArtists().get(0)
        ).getName();
    }

    /**
     * Retrieve the optimal match from the request result.
     * The optimal match is the one with the better score.
     *
     * @return the optimal match from the request result. If no matches are found, return nothing.
     */
    private Optional<AcoustidMatch> getOptimalMatch() {
        try {
            requestResult.getResults().sort((match1, match2) -> (int) ((match1.getScore() - match2.getScore()) * 100));

            return Optional.of(requestResult.getResults().get(
                    requestResult.getResults().size()-1
            ));
        } catch (Exception e) {
            // If there is no matches, return nothing.
            System.out.println(e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    /**
     * Check if a recording contains the same data as the metadata of the file used for the request.
     *
     * @param recording the recording to analyze.
     * @return true if the recording contains the same metadata, false if not.
     */
    private boolean isRecordingMatchingWithMetadata(AcoustidRecording recording) {
        if (recording.getTitle() == null || recording.getArtists() == null || recording.getReleaseGroups() == null) return false;
        if (recording.getArtists().isEmpty() || recording.getReleaseGroups().isEmpty()) return false;

//        boolean isMatchingWithName = recording.getTitle().equals(initialMetadata.getName());
        boolean isMatchingWithArtist = recording.getArtists().stream().anyMatch(artist -> artist.getName().equals(initialMetadata.getArtist()));
        boolean isMatchingWithAlbum = recording.getReleaseGroups().stream().anyMatch(release -> release.getTitle().equals(initialMetadata.getAlbum()));

        System.out.println("ANALYZING THE NEXT RECORDING: "+recording.getId());
//        System.out.println("-- METADATA NAME: "+initialMetadata.getName()+", RECORDING NAME: "+recording.getTitle()+", IS MATCHING ? "+isMatchingWithName);
        System.out.println("-- METADATA ARTIST: "+initialMetadata.getArtist()+", IS MATCHING ? "+isMatchingWithArtist);
        System.out.println("-- METADATA ALBUM: "+initialMetadata.getAlbum()+", IS MATCHING ? "+isMatchingWithAlbum);

        return isMatchingWithAlbum && isMatchingWithArtist;
    }

    /**
     * Tries to fetch an optimal recording matching with the initial metadata given.
     *
     * @param match the match to analyze.
     * @return a recording matching with the initial metadata or nothing.
     */
    private Optional<AcoustidRecording> getOptimalRecordingMatchingMetadata(AcoustidMatch match) {
        for (AcoustidRecording recording: match.getRecordings()) {
            boolean doContainsUsefulInformation = recording.getTitle() != null && recording.getArtists() != null && recording.getReleaseGroups() != null;
            boolean doContainsAlbum = doesRecordingContainsAlbum(recording);
            boolean matchesWithMetadata = isRecordingMatchingWithMetadata(recording);
            System.out.println("Fetching optimal recording - useful info ? "+doContainsUsefulInformation);
            System.out.println("Fetching optimal recording - is album ? "+doContainsAlbum);
            System.out.println("Matching with initial data ? "+matchesWithMetadata);
            if (doContainsUsefulInformation && doContainsAlbum && matchesWithMetadata) return Optional.of(recording);
        }

        return Optional.empty();
    }

    /**
     * Retrieve the optimal recording between all the one proposed.
     * - The optimal recording includes an album.
     * - The optimal recording matches, if possible, with the given metadata.
     *
     * @param match the match containing all recordings.
     * @return the optimal recording or nothing if there is no recordings.
     */
    private Optional<AcoustidRecording> getOptimalRecording(AcoustidMatch match) {
        if (match.getRecordings().isEmpty()) return Optional.empty();

        // We first try to retrieve a recording matching with the initial metadata of the file.
        Optional<AcoustidRecording> matchingRecording = getOptimalRecordingMatchingMetadata(match);
        if (matchingRecording.isPresent()) return matchingRecording;

        // If nothing was found, we try to find another useful recording.
        for (AcoustidRecording recording: match.getRecordings()) {
            boolean doContainsUsefulInformation = recording.getTitle() != null && recording.getArtists() != null && recording.getReleaseGroups() != null;
            boolean doContainsAlbum = doesRecordingContainsAlbum(recording);
            System.out.println("Fetching optimal recording - useful info ? "+doContainsUsefulInformation);
            System.out.println("Fetching optimal recording - is album ? "+doContainsAlbum);
            if (doContainsUsefulInformation && doContainsAlbum) return Optional.of(recording);
        }

        return Optional.empty();
    }

    /**
     * Check if a recording has an album.
     *
     * @param recording the recording to check.
     * @return true if the recording has an album.
     */
    private boolean doesRecordingContainsAlbum(AcoustidRecording recording) {
        if (recording.getReleaseGroups() == null) return false;

        for (AcoustidReleaseGroup releaseGroup: recording.getReleaseGroups()) {
            if (releaseGroup.getType().equals("Album")) return true;
        }

        return false;
    }
}
