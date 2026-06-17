package com.bandfeed.band_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class NotBandMemberException extends BusinessException {
    public NotBandMemberException(UUID userId, UUID bandId) {
        super(BandErrorCode.NOT_BAND_MEMBER);
    }
}
