package com.bandfeed.wiki_service.presentation.dto.response;

import com.bandfeed.wiki_service.domain.model.InstrumentConfig;

import java.util.UUID;

public record InstrumentConfigResponseDto(
        UUID id,
        UUID postId,
        String instrumentType
) {
    public static InstrumentConfigResponseDto from(InstrumentConfig config) {
        return new InstrumentConfigResponseDto(
                config.getId(),
                config.getPostId(),
                config.getInstrumentType()
        );
    }
}
