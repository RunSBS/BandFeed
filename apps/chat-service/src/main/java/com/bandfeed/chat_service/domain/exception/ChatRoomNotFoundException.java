package com.bandfeed.chat_service.domain.exception;

import java.util.UUID;

public class ChatRoomNotFoundException extends RuntimeException {

    public ChatRoomNotFoundException(UUID chatRoomId) {
        super("ChatRoom not found: " + chatRoomId);
    }
}
