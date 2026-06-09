package com.bandfeed.wiki_service.presentation.dto.response;

public record InstrumentConfigResponseDto(
        Long id,
        Long songId,
        String instrumentType,
        String difficulty,
        String notes
) {}
