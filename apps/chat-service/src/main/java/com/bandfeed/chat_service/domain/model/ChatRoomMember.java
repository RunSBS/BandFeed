package com.bandfeed.chat_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomMember {

    private final Long id;
    private final Long chatRoomId;
    private final Long userId;
    private LocalDateTime joinedAt;
    private Long lastReadMessageId;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoomMember(Long chatRoomId, Long userId) {
        this.id = null;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.joinedAt = null;
        this.lastReadMessageId = null;
    }

    private ChatRoomMember(Long id, Long chatRoomId, Long userId, LocalDateTime joinedAt, Long lastReadMessageId) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.joinedAt = joinedAt;
        this.lastReadMessageId = lastReadMessageId;
    }

    public static ChatRoomMember create(Long chatRoomId, Long userId) {
        return ChatRoomMember.builder()
                .chatRoomId(chatRoomId)
                .userId(userId)
                .build();
    }

    public static ChatRoomMember reconstitute(Long id, Long chatRoomId, Long userId,
                                              LocalDateTime joinedAt, Long lastReadMessageId) {
        return new ChatRoomMember(id, chatRoomId, userId, joinedAt, lastReadMessageId);
    }

    public void readMessage(Long messageId) {
        this.lastReadMessageId = messageId;
    }
}
