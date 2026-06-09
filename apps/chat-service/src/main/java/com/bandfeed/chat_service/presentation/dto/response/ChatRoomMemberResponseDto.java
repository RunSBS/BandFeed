package com.bandfeed.chat_service.presentation.dto.response;

import java.time.LocalDateTime;

public record ChatRoomMemberResponseDto(
        Long userId,
        Long chatRoomId,
        LocalDateTime joinedAt,
        Long lastReadMessageId
) {}
