package com.bandfeed.user_service.presentation.dto.request;

import java.util.UUID;

public record FollowRequestDto(
        UUID followeeId
) {}
