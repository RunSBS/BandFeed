package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.SpotifyTrackResult;
import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import com.bandfeed.wiki_service.domain.model.Song;

import java.util.List;
import java.util.UUID;

public interface SongService {

    List<SpotifyTrackResult> searchSpotify(String query);

    Song registerSong(String spotifyTrackId);
    Song findSong(UUID songId);

    InstrumentConfig addInstrumentConfig(UUID songId, String instrumentType,
                                         String difficulty, String notes, UUID registeredBy);
    List<InstrumentConfig> findInstrumentConfigs(UUID songId);
    void deleteInstrumentConfig(UUID configId);
}
