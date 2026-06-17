package com.bandfeed.band_service.presentation.dto.response;

import com.bandfeed.band_service.domain.model.TimelinePost;

import java.time.LocalDateTime;
import java.util.UUID;

public record TimelinePostResponseDto(
        UUID id,
        UUID bandId,
        UUID authorId,
        String title,
        String content,
        LocalDateTime createdAt
) {
    public static TimelinePostResponseDto from(TimelinePost post) {
        return new TimelinePostResponseDto(
                post.getId(),
                post.getBandId(),
                post.getAuthorId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt()
        );
    }
}
