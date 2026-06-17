package com.bandfeed.chat_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ChatMessage {

    private final UUID id;
    private final boolean persisted;
    private final UUID chatRoomId;
    private final UUID senderId;
    private String content;
    private LocalDateTime sentAt;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatMessage(UUID chatRoomId, UUID senderId, String content) {
        this.id = UUID.randomUUID();
        this.persisted = false;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = null;
    }

    private ChatMessage(UUID id, UUID chatRoomId, UUID senderId, String content, LocalDateTime sentAt) {
        this.id = id;
        this.persisted = true;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = sentAt;
    }

    public static ChatMessage create(UUID chatRoomId, UUID senderId, String content) {
        return ChatMessage.builder()
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .content(content)
                .build();
    }

    public static ChatMessage reconstitute(UUID id, UUID chatRoomId, UUID senderId, String content, LocalDateTime sentAt) {
        return new ChatMessage(id, chatRoomId, senderId, content, sentAt);
    }
}
