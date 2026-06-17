package com.bandfeed.band_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class AlreadyBandMemberException extends BusinessException {
    public AlreadyBandMemberException(UUID userId, UUID bandId) {
        super(BandErrorCode.ALREADY_BAND_MEMBER);
    }
}
