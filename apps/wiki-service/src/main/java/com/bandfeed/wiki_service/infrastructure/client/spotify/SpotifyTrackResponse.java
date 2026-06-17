package com.bandfeed.wiki_service.infrastructure.client.spotify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyTrackResponse {
    private String id;
    private String name;
    private String artistName;
    private String albumName;
    private String albumImageUrl;
    private int durationMs;
    private String previewUrl;
}
