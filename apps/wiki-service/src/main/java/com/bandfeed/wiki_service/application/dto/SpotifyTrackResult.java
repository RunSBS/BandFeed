package com.bandfeed.wiki_service.application.dto;

public record SpotifyTrackResult(
        String trackId,
        String title,
        String artist,
        String albumName,
        String albumImageUrl,
        int durationMs,
        String previewUrl
) {}
