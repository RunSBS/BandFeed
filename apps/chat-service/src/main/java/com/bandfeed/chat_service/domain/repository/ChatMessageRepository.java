package com.bandfeed.chat_service.domain.repository;

import com.bandfeed.chat_service.domain.model.ChatMessage;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository {
    Optional<ChatMessage> findById(Long id);
    List<ChatMessage> findByChatRoomIdAndIdLessThanOrderByIdDesc(Long chatRoomId, Long beforeId, int size);
    ChatMessage save(ChatMessage message);
}
