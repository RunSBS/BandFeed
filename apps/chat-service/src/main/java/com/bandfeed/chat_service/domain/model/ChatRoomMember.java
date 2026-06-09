package com.bandfeed.chat_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ChatRoomMember {

    private final UUID id;
    private final boolean persisted;
    private final UUID chatRoomId;
    private final UUID userId;
    private LocalDateTime joinedAt;
    private UUID lastReadMessageId;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoomMember(UUID chatRoomId, UUID userId) {
        this.id = UUID.randomUUID();
        this.persisted = false;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.joinedAt = null;
        this.lastReadMessageId = null;
    }

    private ChatRoomMember(UUID id, UUID chatRoomId, UUID userId, LocalDateTime joinedAt, UUID lastReadMessageId) {
        this.id = id;
        this.persisted = true;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.joinedAt = joinedAt;
        this.lastReadMessageId = lastReadMessageId;
    }

    public static ChatRoomMember create(UUID chatRoomId, UUID userId) {
        return ChatRoomMember.builder()
                .chatRoomId(chatRoomId)
                .userId(userId)
                .build();
    }

    public static ChatRoomMember reconstitute(UUID id, UUID chatRoomId, UUID userId,
                                              LocalDateTime joinedAt, UUID lastReadMessageId) {
        return new ChatRoomMember(id, chatRoomId, userId, joinedAt, lastReadMessageId);
    }

    public void readMessage(UUID messageId) {
        this.lastReadMessageId = messageId;
    }
}
