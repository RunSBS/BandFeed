package com.bandfeed.chat_service.presentation.dto.response;

import com.bandfeed.chat_service.domain.model.ChatMessage;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChatMessageResponseDto(
        UUID id,
        UUID chatRoomId,
        UUID senderId,
        String content,
        LocalDateTime sentAt
) {
    public static ChatMessageResponseDto from(ChatMessage message) {
        return new ChatMessageResponseDto(
                message.getId(),
                message.getChatRoomId(),
                message.getSenderId(),
                message.getContent(),
                message.getSentAt()
        );
    }
}
