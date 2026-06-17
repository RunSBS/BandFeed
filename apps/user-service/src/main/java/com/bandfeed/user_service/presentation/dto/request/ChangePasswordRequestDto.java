package com.bandfeed.user_service.presentation.dto.request;

public record ChangePasswordRequestDto(
        String currentPassword,
        String newPassword
) {}
