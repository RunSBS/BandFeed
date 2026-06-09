package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.SpotifyTrackResult;
import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import com.bandfeed.wiki_service.domain.model.Song;

import java.util.List;

public interface SongService {

    List<SpotifyTrackResult> searchSpotify(String query);

    Song registerSong(String spotifyTrackId, String title, String artist,
                      String albumName, String albumImageUrl, int durationMs);
    Song findSong(Long songId);

    InstrumentConfig addInstrumentConfig(Long songId, String instrumentType,
                                         String difficulty, String notes, Long registeredBy);
    List<InstrumentConfig> findInstrumentConfigs(Long songId);
    void deleteInstrumentConfig(Long configId);
}
