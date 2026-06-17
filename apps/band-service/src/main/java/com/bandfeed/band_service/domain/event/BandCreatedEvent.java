package com.bandfeed.band_service.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record BandCreatedEvent(
        UUID bandId,
        UUID leaderId,
        String bandName,
        LocalDateTime occurredAt
) {}
