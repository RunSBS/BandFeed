package com.bandfeed.wiki_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class InstrumentConfigNotFoundException extends BusinessException {

    public InstrumentConfigNotFoundException(UUID configId) {
        super(WikiErrorCode.INSTRUMENT_CONFIG_NOT_FOUND);
    }
}
