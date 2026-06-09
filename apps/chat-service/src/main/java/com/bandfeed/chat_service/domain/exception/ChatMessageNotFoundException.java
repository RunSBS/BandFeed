package com.bandfeed.chat_service.domain.exception;

import java.util.UUID;

public class ChatMessageNotFoundException extends RuntimeException {

    public ChatMessageNotFoundException(UUID messageId) {
        super("ChatMessage not found: " + messageId);
    }
}
