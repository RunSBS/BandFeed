package com.bandfeed.band_service.presentation.dto.response;

import java.time.LocalDateTime;

public record TimelinePostResponseDto(
        Long id,
        Long bandId,
        Long authorId,
        String title,
        String content,
        LocalDateTime createdAt
) {}
