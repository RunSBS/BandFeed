package com.bandfeed.band_service.domain.event;

import java.time.LocalDateTime;

public record BandMemberJoinedEvent(
        Long bandId,
        Long userId,
        String role,
        LocalDateTime occurredAt
) {}
