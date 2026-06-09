package com.bandfeed.wiki_service.application;

import com.bandfeed.wiki_service.application.dto.command.CreatePostCommand;
import com.bandfeed.wiki_service.application.dto.SpotifyTrackResult;
import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import com.bandfeed.wiki_service.domain.model.Post;
import com.bandfeed.wiki_service.domain.model.Song;

import java.util.List;

public interface WikiService {

    // Spotify
    List<SpotifyTrackResult> searchSpotify(String query);

    // Song
    Song registerSong(String spotifyTrackId, String title, String artist,
                      String albumName, String albumImageUrl, int durationMs);
    Song findSong(Long songId);

    // Post
    Post createPost(CreatePostCommand command);
    Post findPost(Long postId);
    List<Post> findPostsBySong(Long songId);
    Post updatePost(Long postId, String title, String content, Long requesterId);
    void deletePost(Long postId, Long requesterId);

    // InstrumentConfig
    InstrumentConfig addInstrumentConfig(Long songId, String instrumentType,
                                         String difficulty, String notes, Long registeredBy);
    List<InstrumentConfig> findInstrumentConfigs(Long songId);
    void deleteInstrumentConfig(Long configId);
}
