package com.bandfeed.chat_service.domain.event;

import java.time.LocalDateTime;

public record MessageSentEvent(
        Long messageId,
        Long chatRoomId,
        Long senderId,
        String content,
        LocalDateTime sentAt
) {}
