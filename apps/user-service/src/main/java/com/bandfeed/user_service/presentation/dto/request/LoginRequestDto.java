package com.bandfeed.user_service.presentation.dto.request;

public record LoginRequestDto(
        String email,
        String password
) {}
