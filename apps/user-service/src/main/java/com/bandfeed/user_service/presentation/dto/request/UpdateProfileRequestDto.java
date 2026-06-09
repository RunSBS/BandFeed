package com.bandfeed.user_service.presentation.dto.request;

public record UpdateProfileRequestDto(
        String nickname,
        String profileImageUrl,
        String introduction
) {}
