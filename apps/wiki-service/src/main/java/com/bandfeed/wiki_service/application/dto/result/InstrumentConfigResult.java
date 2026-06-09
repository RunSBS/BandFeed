package com.bandfeed.wiki_service.application.dto.result;

public record InstrumentConfigResult(
        Long id,
        Long songId,
        String instrumentType,
        String difficulty,
        String notes
) {}
