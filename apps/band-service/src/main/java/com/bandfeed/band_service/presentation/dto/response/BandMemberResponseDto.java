package com.bandfeed.band_service.presentation.dto.response;

import java.time.LocalDateTime;

public record BandMemberResponseDto(
        Long userId,
        String role,
        LocalDateTime joinedAt
) {}
