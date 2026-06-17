package com.bandfeed.band_service.presentation.dto.response;

import com.bandfeed.band_service.domain.model.Band;

import java.time.LocalDateTime;
import java.util.UUID;

public record BandResponseDto(
        UUID id,
        String name,
        String description,
        UUID leaderId,
        LocalDateTime createdAt
) {
    public static BandResponseDto from(Band band) {
        return new BandResponseDto(
                band.getId(),
                band.getName(),
                band.getDescription(),
                band.getLeaderId(),
                band.getCreatedAt()
        );
    }
}
