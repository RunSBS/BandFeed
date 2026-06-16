package com.bandfeed.band_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class InvitationNotFoundException extends BusinessException {
    public InvitationNotFoundException(UUID bandId, UUID userId) {
        super(BandErrorCode.INVITATION_NOT_FOUND);
    }
}
