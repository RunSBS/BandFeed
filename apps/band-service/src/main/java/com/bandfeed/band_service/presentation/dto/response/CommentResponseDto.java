package com.bandfeed.band_service.presentation.dto.response;

import com.bandfeed.band_service.domain.model.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponseDto(
        UUID id,
        UUID postId,
        UUID authorId,
        String content,
        LocalDateTime createdAt
) {
    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getPostId(),
                comment.getAuthorId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
