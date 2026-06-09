package com.bandfeed.chat_service.domain.exception;

import java.util.UUID;

public class NotChatParticipantException extends RuntimeException {

    public NotChatParticipantException(UUID userId, UUID chatRoomId) {
        super("User " + userId + " is not a participant of chatRoom " + chatRoomId);
    }
}
