package com.bandfeed.band_service.application.dto.result;

import java.time.LocalDateTime;

public record BandMemberResult(
        Long userId,
        String role,
        LocalDateTime joinedAt
) {}
