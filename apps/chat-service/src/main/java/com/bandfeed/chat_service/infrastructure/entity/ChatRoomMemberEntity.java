package com.bandfeed.chat_service.infrastructure.entity;

import com.bandfeed.chat_service.domain.model.ChatRoomMember;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "chat_room_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomMemberEntity extends BaseEntity implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID chatRoomId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID lastReadMessageId;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoomMemberEntity(UUID id, UUID chatRoomId, UUID userId, UUID lastReadMessageId, boolean isNew) {
        this.id = id;
        this.isNew = isNew;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.lastReadMessageId = lastReadMessageId;
    }

    public static ChatRoomMemberEntity from(ChatRoomMember domain) {
        return ChatRoomMemberEntity.builder()
                .id(domain.getId())
                .isNew(!domain.isPersisted())
                .chatRoomId(domain.getChatRoomId())
                .userId(domain.getUserId())
                .lastReadMessageId(domain.getLastReadMessageId())
                .build();
    }

    @Override
    public UUID getId() { return id; }

    @Override
    public boolean isNew() { return isNew; }

    @PostLoad
    @PostPersist
    void markNotNew() { this.isNew = false; }

    public ChatRoomMember toDomain() {
        return ChatRoomMember.reconstitute(id, chatRoomId, userId, getCreatedAt(), lastReadMessageId);
    }

    public void update(ChatRoomMember domain) {
        this.lastReadMessageId = domain.getLastReadMessageId();
    }
}
