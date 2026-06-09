package com.bandfeed.band_service.domain.exception;

import java.util.UUID;

public class BandNotFoundException extends RuntimeException {
    public BandNotFoundException(UUID bandId) {
        super("Band not found: " + bandId);
    }
}
