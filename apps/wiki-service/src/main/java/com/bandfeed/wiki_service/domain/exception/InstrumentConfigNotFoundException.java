package com.bandfeed.wiki_service.domain.exception;

import java.util.UUID;

public class InstrumentConfigNotFoundException extends RuntimeException {

    public InstrumentConfigNotFoundException(UUID configId) {
        super("InstrumentConfig not found: " + configId);
    }
}
