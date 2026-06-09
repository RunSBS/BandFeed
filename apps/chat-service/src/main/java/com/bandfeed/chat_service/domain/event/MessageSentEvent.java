package com.bandfeed.chat_service.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageSentEvent(
        UUID messageId,
        UUID chatRoomId,
        UUID senderId,
        String content,
        LocalDateTime sentAt
) {}
