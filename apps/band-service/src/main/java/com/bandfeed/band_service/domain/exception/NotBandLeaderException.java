package com.bandfeed.band_service.domain.exception;

public class NotBandLeaderException extends RuntimeException {
    public NotBandLeaderException(Long userId) {
        super("User " + userId + " is not the band leader");
    }
}
