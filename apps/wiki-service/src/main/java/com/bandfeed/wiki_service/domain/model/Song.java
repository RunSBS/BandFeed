package com.bandfeed.wiki_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Song {

    private final Long id;
    private final String spotifyTrackId;
    private String title;
    private String artist;
    private String albumName;
    private String albumImageUrl;
    private int durationMs;
    private LocalDateTime registeredAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Song(String spotifyTrackId, String title, String artist, String albumName, String albumImageUrl, int durationMs) {
        this.id = null;
        this.spotifyTrackId = spotifyTrackId;
        this.title = title;
        this.artist = artist;
        this.albumName = albumName;
        this.albumImageUrl = albumImageUrl;
        this.durationMs = durationMs;
        this.registeredAt = null;
    }

    private Song(Long id, String spotifyTrackId, String title, String artist, String albumName,
                 String albumImageUrl, int durationMs, LocalDateTime registeredAt) {
        this.id = id;
        this.spotifyTrackId = spotifyTrackId;
        this.title = title;
        this.artist = artist;
        this.albumName = albumName;
        this.albumImageUrl = albumImageUrl;
        this.durationMs = durationMs;
        this.registeredAt = registeredAt;
    }

    public static Song create(String spotifyTrackId, String title, String artist,
                              String albumName, String albumImageUrl, int durationMs) {
        return Song.builder()
                .spotifyTrackId(spotifyTrackId)
                .title(title)
                .artist(artist)
                .albumName(albumName)
                .albumImageUrl(albumImageUrl)
                .durationMs(durationMs)
                .build();
    }

    public static Song reconstitute(Long id, String spotifyTrackId, String title, String artist,
                                    String albumName, String albumImageUrl, int durationMs, LocalDateTime registeredAt) {
        return new Song(id, spotifyTrackId, title, artist, albumName, albumImageUrl, durationMs, registeredAt);
    }
}
