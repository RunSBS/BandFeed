package com.bandfeed.wiki_service.domain.exception;

public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException(Long songId) {
        super("Song not found: " + songId);
    }
}
