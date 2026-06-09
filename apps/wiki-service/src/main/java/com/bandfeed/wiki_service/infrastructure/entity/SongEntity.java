package com.bandfeed.wiki_service.infrastructure.entity;

import com.bandfeed.wiki_service.domain.model.Song;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "songs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SongEntity extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String spotifyTrackId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    private String albumName;

    private String albumImageUrl;

    @Column(nullable = false)
    private int durationMs;

    @Builder(access = AccessLevel.PRIVATE)
    private SongEntity(UUID id, String spotifyTrackId, String title, String artist,
                       String albumName, String albumImageUrl, int durationMs) {
        this.id = id;
        this.spotifyTrackId = spotifyTrackId;
        this.title = title;
        this.artist = artist;
        this.albumName = albumName;
        this.albumImageUrl = albumImageUrl;
        this.durationMs = durationMs;
    }

    public static SongEntity from(Song domain) {
        return SongEntity.builder()
                .id(domain.getId())
                .spotifyTrackId(domain.getSpotifyTrackId())
                .title(domain.getTitle())
                .artist(domain.getArtist())
                .albumName(domain.getAlbumName())
                .albumImageUrl(domain.getAlbumImageUrl())
                .durationMs(domain.getDurationMs())
                .build();
    }

    public Song toDomain() {
        return Song.reconstitute(id, spotifyTrackId, title, artist, albumName, albumImageUrl, durationMs, getCreatedAt());
    }
}
