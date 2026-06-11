package com.bandfeed.band_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class NotCommentAuthorException extends BusinessException {
    public NotCommentAuthorException(UUID userId) {
        super(BandErrorCode.NOT_COMMENT_AUTHOR);
    }
}
