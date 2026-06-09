package com.bandfeed.wiki_service.domain.exception;

public class DuplicateSongException extends RuntimeException {

    public DuplicateSongException(String spotifyTrackId) {
        super("Duplicate song with Spotify track ID: " + spotifyTrackId);
    }
}
