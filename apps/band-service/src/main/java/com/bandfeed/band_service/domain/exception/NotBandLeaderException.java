package com.bandfeed.band_service.domain.exception;

import java.util.UUID;

public class NotBandLeaderException extends RuntimeException {
    public NotBandLeaderException(UUID userId) {
        super("User " + userId + " is not the band leader");
    }
}
