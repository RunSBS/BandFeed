package com.bandfeed.chat_service.presentation.dto.response;

import com.bandfeed.chat_service.domain.model.ChatRoomMember;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChatRoomMemberResponseDto(
        UUID userId,
        UUID chatRoomId,
        LocalDateTime joinedAt,
        UUID lastReadMessageId
) {
    public static ChatRoomMemberResponseDto from(ChatRoomMember member) {
        return new ChatRoomMemberResponseDto(
                member.getUserId(),
                member.getChatRoomId(),
                member.getJoinedAt(),
                member.getLastReadMessageId()
        );
    }
}
