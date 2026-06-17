package com.bandfeed.wiki_service.infrastructure.client.spotify;

// Spotify Web API 연동 클라이언트
// GET /v1/search?q={keyword}&type=track&limit={limit}&offset={offset}
// GET /v1/tracks/{id}
public interface SpotifyClient {
    SpotifySearchResponse searchTracks(String keyword, int limit, int offset);
    SpotifyTrackResponse getTrack(String trackId);
}
