package com.bandfeed.chat_service.infrastructure.entity;

import com.bandfeed.chat_service.domain.model.ChatRoom;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_rooms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long bandId;

    @Column(nullable = false)
    private String roomName;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoomEntity(Long id, Long bandId, String roomName) {
        this.id = id;
        this.bandId = bandId;
        this.roomName = roomName;
    }

    public static ChatRoomEntity from(ChatRoom domain) {
        return ChatRoomEntity.builder()
                .id(domain.getId())
                .bandId(domain.getBandId())
                .roomName(domain.getRoomName())
                .build();
    }

    public ChatRoom toDomain() {
        return ChatRoom.reconstitute(id, bandId, roomName, getCreatedAt());
    }
}
