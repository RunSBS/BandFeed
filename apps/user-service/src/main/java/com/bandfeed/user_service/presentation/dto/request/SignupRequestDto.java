package com.bandfeed.user_service.presentation.dto.request;

public record SignupRequestDto(
        String email,
        String password,
        String nickname
) {}
