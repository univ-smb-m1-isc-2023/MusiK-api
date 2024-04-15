package com.github.enteraname74.musik.domain.handlerimpl;

import com.github.enteraname74.musik.domain.handler.RemoteMusicCoverRetriever;
import com.github.enteraname74.musik.domain.utils.AppHttpClient;
import com.github.enteraname74.musik.domain.utils.LyristResult;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * Implementation of the RemoteMusicCoverRetriever.
 */
@Component
public class RemoteMusicCoverRetrieverImpl implements RemoteMusicCoverRetriever {

    private final AppHttpClient httpClient;

    @Autowired
    public RemoteMusicCoverRetrieverImpl(AppHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Optional<String> getCoverURL(String musicName, String musicArtist) {
        try {
            String initialPath = "https://lyrist.vercel.app/api/"+musicName+"/"+musicArtist;
            String encodedPath  = initialPath.replace(" ", "%20");
            HttpResponse<String> response = httpClient.doGet(encodedPath);

            Gson gson = new Gson();
            LyristResult lyristResult = gson.fromJson(response.body(), LyristResult.class);

            return Optional.of(lyristResult.image());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
