package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.model.ChatMessage;
import com.bandfeed.chat_service.domain.model.ChatRoom;

import java.util.List;

public interface ChatService {

    // ChatRoom
    ChatRoom findOrCreateRoom(Long userAId, Long userBId);
    ChatRoom findRoom(Long roomId);

    // ChatMessage
    ChatMessage sendMessage(Long chatRoomId, Long senderId, String content);
    List<ChatMessage> findMessages(Long chatRoomId, Long beforeId, int size);
    void readMessage(Long chatRoomId, Long userId, Long messageId);
}
