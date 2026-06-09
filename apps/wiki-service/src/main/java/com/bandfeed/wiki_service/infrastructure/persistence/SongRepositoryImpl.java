package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.domain.model.Song;
import com.bandfeed.wiki_service.domain.repository.SongRepository;
import com.bandfeed.wiki_service.infrastructure.entity.SongEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SongRepositoryImpl implements SongRepository {

    private final JpaSongRepository jpaSongRepository;

    @Override
    public Optional<Song> findById(Long id) {
        return jpaSongRepository.findById(id).map(SongEntity::toDomain);
    }

    @Override
    public Optional<Song> findBySpotifyTrackId(String spotifyTrackId) {
        return jpaSongRepository.findBySpotifyTrackId(spotifyTrackId).map(SongEntity::toDomain);
    }

    @Override
    public Song save(Song song) {
        return jpaSongRepository.save(SongEntity.from(song)).toDomain();
    }

    @Override
    public void delete(Song song) {
        jpaSongRepository.deleteById(song.getId());
    }
}
