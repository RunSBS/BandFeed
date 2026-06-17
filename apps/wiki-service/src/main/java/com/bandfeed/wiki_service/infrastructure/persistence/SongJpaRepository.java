package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.infrastructure.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongJpaRepository extends JpaRepository<SongEntity, UUID> {
    Optional<SongEntity> findBySpotifyTrackId(String spotifyTrackId);
    List<SongEntity> findAllByTitleContainingIgnoreCase(String title);
}
