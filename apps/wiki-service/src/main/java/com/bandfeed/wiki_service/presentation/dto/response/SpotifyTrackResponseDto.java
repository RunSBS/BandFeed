package com.bandfeed.wiki_service.presentation.dto.response;

public record SpotifyTrackResponseDto(
        String trackId,
        String title,
        String artist,
        String albumName,
        String albumImageUrl,
        int durationMs,
        String previewUrl
) {}
