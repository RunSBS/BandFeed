package com.bandfeed.wiki_service.presentation.dto.response;

public record SongResponseDto(
        Long id,
        String spotifyTrackId,
        String title,
        String artist,
        String albumName,
        String albumImageUrl,
        int durationMs
) {}
