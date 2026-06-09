package com.bandfeed.chat_service.presentation.dto.response;

import java.time.LocalDateTime;

public record ChatMessageResponseDto(
        Long id,
        Long chatRoomId,
        Long senderId,
        String content,
        LocalDateTime sentAt
) {}
