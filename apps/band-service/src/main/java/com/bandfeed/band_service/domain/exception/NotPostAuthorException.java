package com.bandfeed.band_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class NotPostAuthorException extends BusinessException {
    public NotPostAuthorException(UUID userId) {
        super(BandErrorCode.NOT_POST_AUTHOR);
    }
}
