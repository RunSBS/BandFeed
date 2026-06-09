package com.bandfeed.chat_service.infrastructure.entity;

import com.bandfeed.chat_service.domain.model.ChatRoom;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "chat_rooms",
        uniqueConstraints = @UniqueConstraint(columnNames = {"participant1_id", "participant2_id"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "participant1_id", nullable = false)
    private Long participant1Id;

    @Column(name = "participant2_id", nullable = false)
    private Long participant2Id;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoomEntity(Long id, Long participant1Id, Long participant2Id) {
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
