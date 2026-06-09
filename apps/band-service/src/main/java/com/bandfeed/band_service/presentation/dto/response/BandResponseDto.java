package com.bandfeed.band_service.presentation.dto.response;

import com.bandfeed.band_service.domain.model.Band;

import java.time.LocalDateTime;

public record BandResponseDto(
        Long id,
        String name,
        String description,
        Long leaderId,
        String status,
        LocalDateTime createdAt
) {
    public static BandResponseDto from(Band band) {
        return new BandResponseDto(
                band.getId(),
                band.getName(),
                band.getDescription(),
                band.getLeaderId(),
                band.getStatus().name(),
                band.getCreatedAt()
        );
    }
}
