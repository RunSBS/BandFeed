package com.bandfeed.band_service.application.dto.command;

import java.util.UUID;

public record CreateBandCommand(
        String name,
        String description,
        UUID leaderId
) {}
