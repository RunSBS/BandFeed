package com.bandfeed.band_service.domain.exception;

import java.util.UUID;

public class AlreadyBandMemberException extends RuntimeException {
    public AlreadyBandMemberException(UUID userId, UUID bandId) {
        super("User " + userId + " is already a member of band " + bandId);
    }
}
