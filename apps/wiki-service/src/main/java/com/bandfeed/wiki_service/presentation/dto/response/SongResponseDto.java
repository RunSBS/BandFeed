package com.bandfeed.wiki_service.presentation.dto.response;

import com.bandfeed.wiki_service.domain.model.Song;

import java.util.UUID;

public record SongResponseDto(
        UUID id,
        String spotifyTrackId,
        String title,
        String artist,
        String albumName,
        String albumImageUrl,
        int durationMs
) {
    public static SongResponseDto from(Song song) {
        return new SongResponseDto(
                song.getId(),
                song.getSpotifyTrackId(),
                song.getTitle(),
                song.getArtist(),
                song.getAlbumName(),
                song.getAlbumImageUrl(),
                song.getDurationMs()
        );
    }
}
