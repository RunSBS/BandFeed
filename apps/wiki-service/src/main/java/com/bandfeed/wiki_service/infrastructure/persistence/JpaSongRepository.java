package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.infrastructure.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSongRepository extends JpaRepository<SongEntity, Long> {
    Optional<SongEntity> findBySpotifyTrackId(String spotifyTrackId);
}
