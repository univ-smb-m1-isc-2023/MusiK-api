package com.github.enteraname74.musik.domain.utils;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * HttpClient of the application.
 */
@Component
public class AppHttpClient {
    private final HttpClient client;

    public AppHttpClient() {
        client = HttpClient.newHttpClient();
    }

    public HttpResponse<String> doGet(String uri) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
