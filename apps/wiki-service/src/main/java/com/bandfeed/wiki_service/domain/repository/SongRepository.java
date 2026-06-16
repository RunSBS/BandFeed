package com.bandfeed.wiki_service.domain.repository;

import com.bandfeed.wiki_service.domain.model.Song;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongRepository {
    Optional<Song> findById(UUID id);
    Optional<Song> findBySpotifyTrackId(String spotifyTrackId);
    List<Song> findAllByTitleContaining(String keyword);
    List<Song> findPopularSongs(int limit);
    Song save(Song song);
    void delete(Song song);
}
