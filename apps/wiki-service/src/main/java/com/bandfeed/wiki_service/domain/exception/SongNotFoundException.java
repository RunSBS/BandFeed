package com.bandfeed.wiki_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class SongNotFoundException extends BusinessException {

    public SongNotFoundException(UUID songId) {
        super(WikiErrorCode.SONG_NOT_FOUND);
    }
}
