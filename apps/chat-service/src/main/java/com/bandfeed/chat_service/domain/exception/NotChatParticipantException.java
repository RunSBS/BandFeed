package com.bandfeed.chat_service.domain.exception;

public class NotChatParticipantException extends RuntimeException {

    public NotChatParticipantException(Long userId, Long chatRoomId) {
        super("User " + userId + " is not a participant of chatRoom " + chatRoomId);
    }
}
