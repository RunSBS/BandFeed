package com.bandfeed.band_service.application.dto.result;

import java.time.LocalDateTime;

public record TimelinePostResult(
        Long id,
        Long bandId,
        Long authorId,
        String title,
        String content,
        LocalDateTime createdAt
) {}
