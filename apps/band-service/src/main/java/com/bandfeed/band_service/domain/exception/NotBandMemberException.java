package com.bandfeed.band_service.domain.exception;

public class NotBandMemberException extends RuntimeException {
    public NotBandMemberException(Long userId, Long bandId) {
        super("User " + userId + " is not a member of band " + bandId);
    }
}
