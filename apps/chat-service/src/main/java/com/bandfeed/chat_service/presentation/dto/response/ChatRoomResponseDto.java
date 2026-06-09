package com.bandfeed.chat_service.presentation.dto.response;

import java.time.LocalDateTime;

public record ChatRoomResponseDto(
        Long id,
        Long bandId,
        String roomName,
        LocalDateTime createdAt
) {}
