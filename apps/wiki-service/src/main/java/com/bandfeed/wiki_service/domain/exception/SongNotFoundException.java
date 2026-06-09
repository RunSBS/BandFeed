package com.bandfeed.wiki_service.domain.exception;

import java.util.UUID;

public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException(UUID songId) {
        super("Song not found: " + songId);
    }
}
