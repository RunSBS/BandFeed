package com.bandfeed.wiki_service.application.dto.result;

import java.time.LocalDateTime;

public record PostResult(
        Long id,
        Long songId,
        Long authorId,
        String title,
        String content,
        LocalDateTime createdAt
) {}
