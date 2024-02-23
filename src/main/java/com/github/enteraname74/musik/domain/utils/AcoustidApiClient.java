package com.github.enteraname74.musik.domain.utils;

import com.github.enteraname74.musik.domain.model.Music;
import com.github.enteraname74.musik.domain.model.MusicMetadata;
import com.github.enteraname74.musik.domain.model.acoustid.AcoustidLookupRequestResult;
import com.google.gson.Gson;

import java.net.http.HttpResponse;

/**
 * Client for the Acoustid API.
 */
public class AcoustidApiClient {
    private final AppHttpClient httpClient;
    private final String API_KEY;

    private final MusicMetadata initialMetadata;

    public AcoustidApiClient(MusicMetadata initialMetadata) {
        this.initialMetadata = initialMetadata;
        this.httpClient = new AppHttpClient();
    }

    /**
     * Retrieve music information from the Acoustid API.
     *
     * @param fingerprint the fingerprint used by the Acoustid API to found music information.
     * @param musicDuration the duration of the music.
     * @return music information about the fingerprint. It can return an empty Music element (beside its id) if no
     * information was found.
     */
    public Music getMusicInformation(
            String fingerprint,
            String musicDuration,
            String musicFileId
    ) {
        String uri = "https://api.acoustid.org/v2/lookup?client="+API_KEY+"&meta=recordings+releasegroups+compress&duration="+musicDuration+"&fingerprint="+fingerprint;

        System.out.println("WILL USE THE FOLLOWING URL");
        System.out.println(uri);

        try {
            HttpResponse<String> response = httpClient.doGet(uri);
            Gson gson = new Gson();
            AcoustidLookupRequestResult requestResult = gson.fromJson(response.body(), AcoustidLookupRequestResult.class);

            AcoustidResultAnalyzer resultAnalyzer = new AcoustidResultAnalyzer(requestResult, initialMetadata);
            MusicMetadata foundInformation = resultAnalyzer.getMusicMetadataFromRequest();
            System.out.println("GOT INFO:");
            System.out.println(foundInformation.getName());
            System.out.println(foundInformation.getAlbum());
            System.out.println(foundInformation.getArtist());

            return Music.ofMetadata(musicFileId, foundInformation);
        } catch (Exception e) {
            System.out.println("CANNOT RETRIEVE RESPONSE");
            System.out.println(e.getLocalizedMessage());
        }

        return Music.emptyMusicInformation(musicFileId);
    }
}
