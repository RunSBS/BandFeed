package com.bandfeed.chat_service.infrastructure.entity;

import com.bandfeed.chat_service.domain.model.ChatMessage;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long chatRoomId;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatMessageEntity(Long id, Long chatRoomId, Long senderId, String content) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
    }

    public static ChatMessageEntity from(ChatMessage domain) {
        return ChatMessageEntity.builder()
                .id(domain.getId())
                .chatRoomId(domain.getChatRoomId())
                .senderId(domain.getSenderId())
                .content(domain.getContent())
                .build();
    }

    public ChatMessage toDomain() {
        return ChatMessage.reconstitute(id, chatRoomId, senderId, content, getCreatedAt());
    }
}
