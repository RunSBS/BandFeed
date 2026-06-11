package com.bandfeed.band_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class CommentNotFoundException extends BusinessException {
    public CommentNotFoundException(UUID commentId) {
        super(BandErrorCode.COMMENT_NOT_FOUND);
    }
}
