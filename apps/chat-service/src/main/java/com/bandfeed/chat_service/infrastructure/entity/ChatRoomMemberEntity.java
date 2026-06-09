package com.bandfeed.chat_service.infrastructure.entity;

import com.bandfeed.chat_service.domain.model.ChatRoomMember;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "chat_room_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomMemberEntity extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID chatRoomId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID lastReadMessageId;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoomMemberEntity(UUID id, UUID chatRoomId, UUID userId, UUID lastReadMessageId) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.lastReadMessageId = lastReadMessageId;
    }

    public static ChatRoomMemberEntity from(ChatRoomMember domain) {
        return ChatRoomMemberEntity.builder()
                .id(domain.getId())
                .chatRoomId(domain.getChatRoomId())
                .userId(domain.getUserId())
                .lastReadMessageId(domain.getLastReadMessageId())
                .build();
    }

    public ChatRoomMember toDomain() {
        return ChatRoomMember.reconstitute(id, chatRoomId, userId, getCreatedAt(), lastReadMessageId);
    }

    public void update(ChatRoomMember domain) {
        this.lastReadMessageId = domain.getLastReadMessageId();
    }
}
