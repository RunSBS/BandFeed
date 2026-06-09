package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.SpotifyTrackResult;
import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import com.bandfeed.wiki_service.domain.model.Song;
import com.bandfeed.wiki_service.domain.repository.InstrumentConfigRepository;
import com.bandfeed.wiki_service.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final InstrumentConfigRepository instrumentConfigRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SpotifyTrackResult> searchSpotify(String query) {
        throw new UnsupportedOperationException("SpotifyClient 구현 후 작성");
    }

    @Override
    public Song registerSong(String spotifyTrackId, String title, String artist,
                             String albumName, String albumImageUrl, int durationMs) {
        songRepository.findBySpotifyTrackId(spotifyTrackId).ifPresent(s -> {
            throw new RuntimeException("Song already registered: " + spotifyTrackId);
        });
        Song song = Song.create(spotifyTrackId, title, artist, albumName, albumImageUrl, durationMs);
        return songRepository.save(song);
    }

    @Override
    @Transactional(readOnly = true)
    public Song findSong(Long songId) {
        return songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found: " + songId));
    }

    @Override
    public InstrumentConfig addInstrumentConfig(Long songId, String instrumentType,
                                                String difficulty, String notes, Long registeredBy) {
        InstrumentConfig config = InstrumentConfig.create(songId, instrumentType, difficulty, notes, registeredBy);
        return instrumentConfigRepository.save(config);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstrumentConfig> findInstrumentConfigs(Long songId) {
        return instrumentConfigRepository.findAllBySongId(songId);
    }

    @Override
    public void deleteInstrumentConfig(Long configId) {
        throw new UnsupportedOperationException("InstrumentConfigRepository.findById 추가 후 작성");
    }
}
