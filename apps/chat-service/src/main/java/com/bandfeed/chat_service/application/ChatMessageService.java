package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.model.ChatMessage;

import java.util.List;
import java.util.UUID;

public interface ChatMessageService {

    ChatMessage sendMessage(UUID chatRoomId, UUID senderId, String content);
    List<ChatMessage> findMessages(UUID chatRoomId, UUID userId, int size);
    void readMessage(UUID chatRoomId, UUID userId, UUID messageId);
    long countTotalUnread(UUID userId);
}
