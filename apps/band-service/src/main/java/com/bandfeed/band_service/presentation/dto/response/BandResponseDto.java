package com.bandfeed.band_service.presentation.dto.response;

public record BandResponseDto(
        Long id,
        String name,
        String description,
        Long leaderId,
        String status
) {}
