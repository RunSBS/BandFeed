package com.bandfeed.chat_service.domain.exception;

public class ChatMessageNotFoundException extends RuntimeException {

    public ChatMessageNotFoundException(Long messageId) {
        super("ChatMessage not found: " + messageId);
    }
}
