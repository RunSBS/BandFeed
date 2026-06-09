package com.bandfeed.band_service.application.dto.result;

public record BandResult(
        Long id,
        String name,
        String description,
        Long leaderId,
        String status
) {}
