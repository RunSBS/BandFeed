package com.bandfeed.wiki_service.domain.exception;

import common.exception.BusinessException;

public class DuplicateSongException extends BusinessException {

    public DuplicateSongException(String spotifyTrackId) {
        super(WikiErrorCode.DUPLICATE_SONG);
    }
}
