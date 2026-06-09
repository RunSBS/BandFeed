package com.bandfeed.band_service.presentation.dto.response;

import com.bandfeed.band_service.domain.model.Comment;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        Long postId,
        Long authorId,
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
