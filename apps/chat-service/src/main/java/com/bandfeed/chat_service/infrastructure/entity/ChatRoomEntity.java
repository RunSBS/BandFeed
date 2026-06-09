package com.bandfeed.chat_service.infrastructure.entity;

import com.bandfeed.chat_service.domain.model.ChatRoom;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(
        name = "chat_rooms",
        uniqueConstraints = @UniqueConstraint(columnNames = {"participant1_id", "participant2_id"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomEntity extends BaseEntity implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "participant1_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID participant1Id;

    @Column(name = "participant2_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID participant2Id;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoomEntity(UUID id, UUID participant1Id, UUID participant2Id, boolean isNew) {
        this.id = id;
        this.isNew = isNew;
        this.participant1Id = participant1Id;
        this.participant2Id = participant2Id;
    }

    public static ChatRoomEntity from(ChatRoom domain) {
        return ChatRoomEntity.builder()
                .id(domain.getId())
                .isNew(!domain.isPersisted())
                .participant1Id(domain.getParticipant1Id())
                .participant2Id(domain.getParticipant2Id())
                .build();
    }

    @Override
    public UUID getId() { return id; }

    @Override
    public boolean isNew() { return isNew; }

    @PostLoad
    @PostPersist
    void markNotNew() { this.isNew = false; }

    public ChatRoom toDomain() {
        return ChatRoom.reconstitute(id, participant1Id, participant2Id, getCreatedAt());
    }
}
