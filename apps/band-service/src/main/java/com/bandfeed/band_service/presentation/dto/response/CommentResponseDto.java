package com.bandfeed.band_service.presentation.dto.response;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        Long postId,
        Long authorId,
        String content,
        LocalDateTime createdAt
) {}
