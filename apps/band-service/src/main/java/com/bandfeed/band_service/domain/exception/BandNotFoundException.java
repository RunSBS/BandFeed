package com.bandfeed.band_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class BandNotFoundException extends BusinessException {
    public BandNotFoundException(UUID bandId) {
        super(BandErrorCode.BAND_NOT_FOUND);
    }
}
