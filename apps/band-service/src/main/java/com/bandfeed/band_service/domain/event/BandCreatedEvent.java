package com.bandfeed.band_service.domain.event;

import java.time.LocalDateTime;

public record BandCreatedEvent(
        Long bandId,
        Long leaderId,
        String bandName,
        LocalDateTime occurredAt
) {}
