package com.bandfeed.wiki_service.infrastructure.client.spotify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// GET /v1/search?type=track 응답 원본
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyApiSearchResponse {
    private TracksPage tracks;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TracksPage {
        private List<SpotifyApiTrack> items;
    }
}
