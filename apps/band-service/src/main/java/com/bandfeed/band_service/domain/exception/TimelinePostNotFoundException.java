package com.bandfeed.band_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class TimelinePostNotFoundException extends BusinessException {
    public TimelinePostNotFoundException(UUID postId) {
        super(BandErrorCode.TIMELINE_POST_NOT_FOUND);
    }
}
