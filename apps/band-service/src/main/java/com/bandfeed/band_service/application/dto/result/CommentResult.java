package com.bandfeed.band_service.application.dto.result;

import java.time.LocalDateTime;

public record CommentResult(
        Long id,
        Long postId,
        Long authorId,
        String content,
        LocalDateTime createdAt
) {}
