package com.bandfeed.chat_service.application.dto.result;

import java.time.LocalDateTime;

public record ChatMessageResult(
        Long id,
        Long chatRoomId,
        Long senderId,
        String content,
        LocalDateTime sentAt
) {}
