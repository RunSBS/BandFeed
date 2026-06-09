package com.bandfeed.user_service.presentation.dto.response;

import com.bandfeed.user_service.domain.model.Follow;

import java.time.LocalDateTime;
import java.util.UUID;

public record FollowResponseDto(
        UUID followerId,
        UUID followeeId,
        LocalDateTime followedAt
) {
    public static FollowResponseDto from(Follow follow) {
        return new FollowResponseDto(
                follow.getFollowerId(),
                follow.getFolloweeId(),
                follow.getFollowedAt()
        );
    }
}
