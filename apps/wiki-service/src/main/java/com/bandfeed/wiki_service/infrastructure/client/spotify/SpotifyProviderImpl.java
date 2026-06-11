package com.bandfeed.wiki_service.infrastructure.client.spotify;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpotifyProviderImpl implements SpotifyClient {

    private static final String API_BASE_URL = "https://api.spotify.com/v1";
    private static final String MARKET = "KR";
    private static final int ALBUM_IMAGE_INDEX = 1; // 300x300

    private final SpotifyTokenManager spotifyTokenManager;
    private final RestClient restClient = RestClient.create(API_BASE_URL);

    @Override
    public SpotifySearchResponse searchTracks(String keyword, int limit, int offset) {
        SpotifyApiSearchResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                        .queryParam("q", keyword)
                        .queryParam("type", "track")
                        .queryParam("market", MARKET)
                        .queryParam("limit", limit)
                        .queryParam("offset", offset)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + spotifyTokenManager.getAccessToken())
                .retrieve()
                .body(SpotifyApiSearchResponse.class);

        List<SpotifyTrackItem> items = response.getTracks().getItems().stream()
                .map(this::toTrackItem)
                .toList();
        return new SpotifySearchResponse(items);
    }

    @Override
    public SpotifyTrackResponse getTrack(String trackId) {
        SpotifyApiTrack track = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/tracks/{id}")
                        .queryParam("market", MARKET)
                        .build(trackId))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + spotifyTokenManager.getAccessToken())
                .retrieve()
                .body(SpotifyApiTrack.class);

        return new SpotifyTrackResponse(
                track.getId(),
                track.getName(),
                extractArtistName(track),
                track.getAlbum().getName(),
                extractAlbumImageUrl(track),
                track.getDurationMs(),
                track.getPreviewUrl()
        );
    }

    private SpotifyTrackItem toTrackItem(SpotifyApiTrack track) {
        return new SpotifyTrackItem(
                track.getId(),
                track.getName(),
                extractArtistName(track),
                track.getAlbum().getName(),
                extractAlbumImageUrl(track),
                track.getDurationMs(),
                track.getPreviewUrl()
        );
    }

    private String extractArtistName(SpotifyApiTrack track) {
        return track.getArtists().isEmpty() ? null : track.getArtists().get(0).getName();
    }

    private String extractAlbumImageUrl(SpotifyApiTrack track) {
        List<SpotifyApiImage> images = track.getAlbum().getImages();
        if (images.isEmpty()) {
            return null;
        }
        int index = Math.min(ALBUM_IMAGE_INDEX, images.size() - 1);
        return images.get(index).getUrl();
    }
}
