package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.domain.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findBySpotifyTrackId(String spotifyTrackId);
}
