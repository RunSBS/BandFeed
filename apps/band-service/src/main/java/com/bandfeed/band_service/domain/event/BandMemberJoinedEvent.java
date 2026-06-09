package com.bandfeed.band_service.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record BandMemberJoinedEvent(
        UUID bandId,
        UUID userId,
        String role,
        LocalDateTime occurredAt
) {}
