package com.bandfeed.user_service.application.dto.result;

import java.time.LocalDateTime;

public record FollowResult(
        Long followerId,
        Long followeeId,
        LocalDateTime followedAt
) {}
