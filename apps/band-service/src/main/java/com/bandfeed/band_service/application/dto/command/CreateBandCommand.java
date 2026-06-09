package com.bandfeed.band_service.application.dto.command;

public record CreateBandCommand(
        String name,
        String description,
        Long leaderId
) {}
