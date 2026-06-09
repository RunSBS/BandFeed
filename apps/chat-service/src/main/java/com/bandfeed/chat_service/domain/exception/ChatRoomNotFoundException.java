package com.bandfeed.chat_service.domain.exception;

public class ChatRoomNotFoundException extends RuntimeException {

    public ChatRoomNotFoundException(Long chatRoomId) {
        super("ChatRoom not found: " + chatRoomId);
    }
}
