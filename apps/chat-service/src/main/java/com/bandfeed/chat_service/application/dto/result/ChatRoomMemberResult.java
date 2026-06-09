package com.bandfeed.chat_service.application.dto.result;

import java.time.LocalDateTime;

public record ChatRoomMemberResult(
        Long userId,
        Long chatRoomId,
        LocalDateTime joinedAt,
        Long lastReadMessageId
) {}
