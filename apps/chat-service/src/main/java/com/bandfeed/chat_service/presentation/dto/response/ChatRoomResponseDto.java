package com.bandfeed.chat_service.presentation.dto.response;

import com.bandfeed.chat_service.domain.model.ChatRoom;

import java.time.LocalDateTime;

public record ChatRoomResponseDto(
        Long id,
        Long participant1Id,
        Long participant2Id,
        LocalDateTime createdAt
) {
    public static ChatRoomResponseDto from(ChatRoom chatRoom) {
        return new ChatRoomResponseDto(
                chatRoom.getId(),
                chatRoom.getParticipant1Id(),
                chatRoom.getParticipant2Id(),
                chatRoom.getCreatedAt()
        );
    }
}
