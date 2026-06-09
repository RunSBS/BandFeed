package com.bandfeed.chat_service.presentation.dto.response;

import com.bandfeed.chat_service.domain.model.ChatRoomMember;

import java.time.LocalDateTime;

public record ChatRoomMemberResponseDto(
        Long userId,
        Long chatRoomId,
        LocalDateTime joinedAt,
        Long lastReadMessageId
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
