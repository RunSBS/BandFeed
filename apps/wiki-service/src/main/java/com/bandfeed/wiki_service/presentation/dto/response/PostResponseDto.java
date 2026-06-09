package com.bandfeed.wiki_service.presentation.dto.response;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        Long songId,
        Long authorId,
        String title,
        String content,
        LocalDateTime createdAt
) {}
