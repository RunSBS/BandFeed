package com.bandfeed.wiki_service.presentation.dto.response;

import com.bandfeed.wiki_service.domain.model.Post;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostResponseDto(
        UUID id,
        UUID songId,
        UUID authorId,
        String authorNickname,
        String authorProfileImageUrl,
        UUID bandId,
        String bandName,
        String bandImageUrl,
        String title,
        String content,
        LocalDateTime createdAt
) {
    public static PostResponseDto from(Post post, String authorNickname, String authorProfileImageUrl) {
        return new PostResponseDto(
                post.getId(),
                post.getSongId(),
                post.getAuthorId(),
                authorNickname,
                authorProfileImageUrl,
                post.getBandId(),
                post.getBandName(),
                post.getBandImageUrl(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt()
        );
    }
}
