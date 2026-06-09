package com.bandfeed.chat_service.presentation.dto.response;

import com.bandfeed.chat_service.domain.model.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageResponseDto(
        Long id,
        Long chatRoomId,
        Long senderId,
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
