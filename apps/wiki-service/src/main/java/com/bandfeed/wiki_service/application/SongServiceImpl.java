package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.SpotifyTrackResult;
import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import com.bandfeed.wiki_service.domain.model.Song;
import com.bandfeed.wiki_service.domain.exception.DuplicateSongException;
import com.bandfeed.wiki_service.domain.exception.InstrumentConfigNotFoundException;
import com.bandfeed.wiki_service.domain.exception.SongNotFoundException;
import com.bandfeed.wiki_service.domain.exception.SpotifyApiException;
import com.bandfeed.wiki_service.domain.repository.InstrumentConfigRepository;
import com.bandfeed.wiki_service.domain.repository.SongRepository;
import com.bandfeed.wiki_service.infrastructure.client.spotify.SpotifyClient;
import com.bandfeed.wiki_service.infrastructure.client.spotify.SpotifyTrackItem;
import com.bandfeed.wiki_service.infrastructure.client.spotify.SpotifyTrackResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private static final int SEARCH_LIMIT = 10;
    private static final int SEARCH_OFFSET = 0;

    private final SongRepository songRepository;
    private final InstrumentConfigRepository instrumentConfigRepository;
    private final SpotifyClient spotifyClient;

    @Override
    public List<SpotifyTrackResult> searchSpotify(String query) {
        List<Song> cached = songRepository.findAllByTitleContaining(query);
        if (!cached.isEmpty()) {
            return cached.stream().map(this::toResult).toList();
        }

        return spotifyClient.searchTracks(query, SEARCH_LIMIT, SEARCH_OFFSET).getItems().stream()
                .map(this::saveIfAbsent)
                .map(this::toResult)
                .toList();
    }

    private Song saveIfAbsent(SpotifyTrackItem item) {
        return songRepository.findBySpotifyTrackId(item.getId())
                .orElseGet(() -> songRepository.save(Song.create(
                        item.getId(),
                        item.getName(),
                        item.getArtistName(),
                        item.getAlbumName(),
                        item.getAlbumImageUrl(),
                        item.getDurationMs())));
    }

    private SpotifyTrackResult toResult(Song song) {
        return new SpotifyTrackResult(
                song.getSpotifyTrackId(),
                song.getTitle(),
                song.getArtist(),
                song.getAlbumName(),
                song.getAlbumImageUrl(),
                song.getDurationMs(),
                null);
    }

    @Override
    public Song registerSong(String spotifyTrackId) {
        songRepository.findBySpotifyTrackId(spotifyTrackId).ifPresent(s -> {
            throw new DuplicateSongException(spotifyTrackId);
        });
        SpotifyTrackResponse track;
        try {
            track = spotifyClient.getTrack(spotifyTrackId);
        } catch (RestClientException e) {
            log.error("Spotify getTrack 호출 실패: spotifyTrackId={}", spotifyTrackId, e);
            throw new SpotifyApiException();
        }
        Song song = Song.create(track.getId(), track.getName(), track.getArtistName(),
                track.getAlbumName(), track.getAlbumImageUrl(), track.getDurationMs());
        return songRepository.save(song);
    }

    @Override
    @Transactional(readOnly = true)
    public Song findSong(UUID songId) {
        return songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException(songId));
    }

    @Override
    public InstrumentConfig addInstrumentConfig(UUID songId, String instrumentType,
                                                String difficulty, String notes, UUID registeredBy) {
        InstrumentConfig config = InstrumentConfig.create(songId, instrumentType, difficulty, notes, registeredBy);
        return instrumentConfigRepository.save(config);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InstrumentConfig> findInstrumentConfigs(UUID songId) {
        return instrumentConfigRepository.findAllBySongId(songId);
    }

    @Override
    public void deleteInstrumentConfig(UUID configId) {
        InstrumentConfig config = instrumentConfigRepository.findById(configId)
                .orElseThrow(() -> new InstrumentConfigNotFoundException(configId));
        instrumentConfigRepository.delete(config);
    }
}
