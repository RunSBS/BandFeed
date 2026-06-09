package com.bandfeed.band_service.domain.exception;

public class AlreadyBandMemberException extends RuntimeException {
    public AlreadyBandMemberException(Long userId, Long bandId) {
        super("User " + userId + " is already a member of band " + bandId);
    }
}
