package com.bandfeed.chat_service.infrastructure.entity;

import com.bandfeed.chat_service.domain.model.ChatMessage;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "chat_messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageEntity extends BaseEntity implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID chatRoomId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID senderId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatMessageEntity(UUID id, UUID chatRoomId, UUID senderId, String content, boolean isNew) {
        this.id = id;
        this.isNew = isNew;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.content = content;
    }

    public static ChatMessageEntity from(ChatMessage domain) {
        return ChatMessageEntity.builder()
                .id(domain.getId())
                .isNew(!domain.isPersisted())
                .chatRoomId(domain.getChatRoomId())
                .senderId(domain.getSenderId())
                .content(domain.getContent())
                .build();
    }

    @Override
    public UUID getId() { return id; }

    @Override
    public boolean isNew() { return isNew; }

    @PostLoad
    @PostPersist
    void markNotNew() { this.isNew = false; }

    public ChatMessage toDomain() {
        return ChatMessage.reconstitute(id, chatRoomId, senderId, content, getCreatedAt());
    }
}
