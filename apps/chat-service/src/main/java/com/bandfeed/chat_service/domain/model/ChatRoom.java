package com.bandfeed.chat_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoom {

    private final Long id;
    private final Long bandId;
    private String roomName;
    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private ChatRoom(Long bandId, String roomName) {
        this.id = null;
        this.bandId = bandId;
        this.roomName = roomName;
        this.createdAt = null;
    }

    private ChatRoom(Long id, Long bandId, String roomName, LocalDateTime createdAt) {
        this.id = id;
        this.bandId = bandId;
        this.roomName = roomName;
        this.createdAt = createdAt;
    }

    public static ChatRoom create(Long bandId, String roomName) {
        return ChatRoom.builder()
                .bandId(bandId)
                .roomName(roomName)
                .build();
    }

    public static ChatRoom reconstitute(Long id, Long bandId, String roomName, LocalDateTime createdAt) {
        return new ChatRoom(id, bandId, roomName, createdAt);
    }
}
