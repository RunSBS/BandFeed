package com.bandfeed.chat_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoom {

    private final Long id;
    private final Long participant1Id;  // 두 참여자 중 ID가 작은 쪽
    private final Long participant2Id;  // 두 참여자 중 ID가 큰 쪽
    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoom(Long participant1Id, Long participant2Id) {
        this.id = null;
        this.participant1Id = participant1Id;
        this.participant2Id = participant2Id;
        this.createdAt = null;
    }

    private ChatRoom(Long id, Long participant1Id, Long participant2Id, LocalDateTime createdAt) {
        this.id = id;
        this.participant1Id = participant1Id;
        this.participant2Id = participant2Id;
        this.createdAt = createdAt;
    }

    public static ChatRoom create(Long userAId, Long userBId) {
        Long p1 = Math.min(userAId, userBId);
        Long p2 = Math.max(userAId, userBId);
        return ChatRoom.builder()
                .participant1Id(p1)
                .participant2Id(p2)
                .build();
    }

    public static ChatRoom reconstitute(Long id, Long participant1Id, Long participant2Id, LocalDateTime createdAt) {
        return new ChatRoom(id, participant1Id, participant2Id, createdAt);
    }
}
