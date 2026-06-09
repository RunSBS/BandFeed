package com.bandfeed.user_service.application.dto.result;

public record LoginResult(
        String accessToken,
        String refreshToken,
        UserResult user
) {}
