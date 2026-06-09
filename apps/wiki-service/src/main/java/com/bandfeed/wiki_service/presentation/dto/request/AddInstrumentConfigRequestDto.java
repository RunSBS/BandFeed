package com.bandfeed.wiki_service.presentation.dto.request;

public record AddInstrumentConfigRequestDto(
        String instrumentType,
        String difficulty,
        String notes
) {}
