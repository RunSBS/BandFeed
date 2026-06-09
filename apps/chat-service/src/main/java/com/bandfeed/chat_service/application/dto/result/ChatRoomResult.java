package com.bandfeed.chat_service.application.dto.result;

import java.time.LocalDateTime;

public record ChatRoomResult(
        Long id,
        Long bandId,
        String roomName,
        LocalDateTime createdAt
) {}
