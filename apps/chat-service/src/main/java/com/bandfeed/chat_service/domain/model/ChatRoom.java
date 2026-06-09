package com.bandfeed.chat_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ChatRoom {

    private final UUID id;
    private final boolean persisted;
    private final UUID participant1Id;  // 두 참여자 중 compareTo가 작은 쪽
    private final UUID participant2Id;  // 두 참여자 중 compareTo가 큰 쪽
    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoom(UUID participant1Id, UUID participant2Id) {
        this.id = UUID.randomUUID();
        this.persisted = false;
        this.participant1Id = participant1Id;
        this.participant2Id = participant2Id;
        this.createdAt = null;
    }

    private ChatRoom(UUID id, UUID participant1Id, UUID participant2Id, LocalDateTime createdAt) {
        this.id = id;
        this.persisted = true;
        this.participant1Id = participant1Id;
        this.participant2Id = participant2Id;
        this.createdAt = createdAt;
    }

    public static ChatRoom create(UUID userAId, UUID userBId) {
        UUID p1 = userAId.compareTo(userBId) < 0 ? userAId : userBId;
        UUID p2 = userAId.compareTo(userBId) < 0 ? userBId : userAId;
        return ChatRoom.builder()
                .participant1Id(p1)
                .participant2Id(p2)
                .build();
    }

    public static ChatRoom reconstitute(UUID id, UUID participant1Id, UUID participant2Id, LocalDateTime createdAt) {
        return new ChatRoom(id, participant1Id, participant2Id, createdAt);
    }
}
