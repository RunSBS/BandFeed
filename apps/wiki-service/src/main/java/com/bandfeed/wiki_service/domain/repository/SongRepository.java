package com.bandfeed.wiki_service.domain.repository;

import com.bandfeed.wiki_service.domain.model.Song;

import java.util.Optional;

public interface SongRepository {
    Optional<Song> findById(Long id);
    Optional<Song> findBySpotifyTrackId(String spotifyTrackId);
    Song save(Song song);
    void delete(Song song);
}
