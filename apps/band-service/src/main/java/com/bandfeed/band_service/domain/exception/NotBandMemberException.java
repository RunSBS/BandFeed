package com.bandfeed.band_service.domain.exception;

import java.util.UUID;

public class NotBandMemberException extends RuntimeException {
    public NotBandMemberException(UUID userId, UUID bandId) {
        super("User " + userId + " is not a member of band " + bandId);
    }
}
