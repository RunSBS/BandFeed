package com.bandfeed.chat_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessage {

    private final Long id;
    private final Long chatRoomId;
    private final Long senderId;
    private String content;
    private LocalDateTime sentAt;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatMessage(Long chatRoomId, Long senderId, String content) {
        this.id = null;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = null;
    }

    private ChatMessage(Long id, Long chatRoomId, Long senderId, String content, LocalDateTime sentAt) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = sentAt;
    }

    public static ChatMessage create(Long chatRoomId, Long senderId, String content) {
        return ChatMessage.builder()
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .content(content)
                .build();
    }

    public static ChatMessage reconstitute(Long id, Long chatRoomId, Long senderId, String content, LocalDateTime sentAt) {
        return new ChatMessage(id, chatRoomId, senderId, content, sentAt);
    }
}
