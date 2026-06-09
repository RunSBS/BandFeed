package com.bandfeed.chat_service.infrastructure.entity;

import com.bandfeed.chat_service.domain.model.ChatRoom;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(
        name = "chat_rooms",
        uniqueConstraints = @UniqueConstraint(columnNames = {"participant1_id", "participant2_id"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomEntity extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "participant1_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID participant1Id;

    @Column(name = "participant2_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID participant2Id;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoomEntity(UUID id, UUID participant1Id, UUID participant2Id) {
        this.id = id;
        this.participant1Id = participant1Id;
        this.participant2Id = participant2Id;
    }

    public static ChatRoomEntity from(ChatRoom domain) {
        return ChatRoomEntity.builder()
                .id(domain.getId())
                .participant1Id(domain.getParticipant1Id())
                .participant2Id(domain.getParticipant2Id())
                .build();
    }

    public ChatRoom toDomain() {
        return ChatRoom.reconstitute(id, participant1Id, participant2Id, getCreatedAt());
    }
}
