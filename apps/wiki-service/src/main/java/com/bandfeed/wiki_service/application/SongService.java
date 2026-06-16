package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.domain.model.Song;

import java.util.List;
import java.util.UUID;

public interface SongService {

    List<Song> searchSpotify(String query);

    Song registerSong(String spotifyTrackId);
    Song findSong(UUID songId);
}
