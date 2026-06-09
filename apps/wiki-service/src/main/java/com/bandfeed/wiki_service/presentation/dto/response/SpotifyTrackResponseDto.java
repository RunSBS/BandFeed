package com.bandfeed.wiki_service.presentation.dto.response;

import com.bandfeed.wiki_service.application.dto.SpotifyTrackResult;

public record SpotifyTrackResponseDto(
        String trackId,
        String title,
        String artist,
        String albumName,
        String albumImageUrl,
        int durationMs,
        String previewUrl
) {
    public static SpotifyTrackResponseDto from(SpotifyTrackResult result) {
        return new SpotifyTrackResponseDto(
                result.trackId(),
                result.title(),
                result.artist(),
                result.albumName(),
                result.albumImageUrl(),
                result.durationMs(),
                result.previewUrl()
        );
    }
}
