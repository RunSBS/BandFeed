package com.bandfeed.user_service.application.dto.command;

public record CreateUserCommand(
        String email,
        String password,
        String nickname,
        String profileImageUrl
) {}
