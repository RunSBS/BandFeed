package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.model.ChatMessage;

import java.util.List;

public interface ChatMessageService {

    ChatMessage sendMessage(Long chatRoomId, Long senderId, String content);
    List<ChatMessage> findMessages(Long chatRoomId, Long beforeId, int size);
    void readMessage(Long chatRoomId, Long userId, Long messageId);
}
