package com.bandfeed.wiki_service.infrastructure.client.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// GET /v1/search, GET /v1/tracks/{id} 응답에 포함되는 트랙 원본 데이터
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyApiTrack {
    private String id;
    private String name;
    private List<SpotifyApiArtist> artists;
    private SpotifyApiAlbum album;
    @JsonProperty("duration_ms")
    private int durationMs;
    @JsonProperty("preview_url")
    private String previewUrl;
}
