package com.bandfeed.wiki_service.domain.exception;

import common.exception.BusinessException;

public class SpotifyApiException extends BusinessException {

    public SpotifyApiException() {
        super(WikiErrorCode.SPOTIFY_API_ERROR);
    }
}
