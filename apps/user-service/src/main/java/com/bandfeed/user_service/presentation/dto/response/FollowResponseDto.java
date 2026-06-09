package com.bandfeed.user_service.presentation.dto.response;

import java.time.LocalDateTime;

public record FollowResponseDto(
        Long followerId,
        Long followeeId,
        LocalDateTime followedAt
) {}
