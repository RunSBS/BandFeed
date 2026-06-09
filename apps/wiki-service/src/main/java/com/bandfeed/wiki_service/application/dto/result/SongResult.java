package com.bandfeed.wiki_service.application.dto.result;

public record SongResult(
        Long id,
        String spotifyTrackId,
        String title,
        String artist,
        String albumName,
        String albumImageUrl,
        int durationMs
) {}
