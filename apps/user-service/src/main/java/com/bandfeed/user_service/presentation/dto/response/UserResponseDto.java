package com.bandfeed.user_service.presentation.dto.response;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String email,
        String nickname,
        String profileImageUrl,
        String introduction,
        String role,
        LocalDateTime createdAt
) {}
