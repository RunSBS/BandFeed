package com.bandfeed.band_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class NotBandLeaderException extends BusinessException {
    public NotBandLeaderException(UUID userId) {
        super(BandErrorCode.NOT_BAND_LEADER);
    }
}
