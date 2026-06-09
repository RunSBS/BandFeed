package com.bandfeed.user_service.application.dto.result;

import java.time.LocalDateTime;

public record UserResult(
        Long id,
        String email,
        String nickname,
        String profileImageUrl,
        String introduction,
        String role,
        LocalDateTime createdAt
) {}
