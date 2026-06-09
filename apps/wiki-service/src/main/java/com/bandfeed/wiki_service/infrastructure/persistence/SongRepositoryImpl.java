package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.domain.model.Song;
import com.bandfeed.wiki_service.domain.repository.SongRepository;
import com.bandfeed.wiki_service.infrastructure.entity.SongEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SongRepositoryImpl implements SongRepository {

    private final SongJpaRepository jpa;

    @Override
    public Optional<Song> findById(UUID id) {
        return jpa.findById(id).map(SongEntity::toDomain);
    }

    @Override
    public Optional<Song> findBySpotifyTrackId(String spotifyTrackId) {
        return jpa.findBySpotifyTrackId(spotifyTrackId).map(SongEntity::toDomain);
    }

    @Override
    public Song save(Song song) {
        return jpa.save(SongEntity.from(song)).toDomain();
    }

    @Override
    public void delete(Song song) {
        jpa.deleteById(song.getId());
    }
}
