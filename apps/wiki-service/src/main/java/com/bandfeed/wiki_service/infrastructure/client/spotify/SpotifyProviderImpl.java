package com.bandfeed.wiki_service.infrastructure.client.spotify;

import org.springframework.stereotype.Component;

@Component
public class SpotifyProviderImpl implements SpotifyClient {

    @Override
    public SpotifySearchResponse searchTracks(String keyword, int limit, int offset) {
        return null;
    }

    @Override
    public SpotifyTrackResponse getTrack(String trackId) {
        return null;
    }
}
