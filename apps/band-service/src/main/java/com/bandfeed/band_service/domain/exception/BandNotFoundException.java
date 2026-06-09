package com.bandfeed.band_service.domain.exception;

public class BandNotFoundException extends RuntimeException {
    public BandNotFoundException(Long bandId) {
        super("Band not found: " + bandId);
    }
}
