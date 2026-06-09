package com.bandfeed.user_service.presentation.dto.response;

public record LoginResponseDto(
        String accessToken,
        String refreshToken
) {}
