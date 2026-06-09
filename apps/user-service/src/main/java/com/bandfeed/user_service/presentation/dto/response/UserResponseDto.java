package com.bandfeed.user_service.presentation.dto.response;

import com.bandfeed.user_service.domain.model.User;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String email,
        String nickname,
        String profileImageUrl,
        String introduction,
        String role,
        LocalDateTime createdAt
) {
    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getProfileImageUrl(),
                user.getIntroduction(),
                user.getRole().name(),
                user.getCreatedAt()
        );
    }
}
