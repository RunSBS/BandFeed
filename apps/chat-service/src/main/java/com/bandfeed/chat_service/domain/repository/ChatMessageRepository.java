package com.bandfeed.chat_service.domain.repository;

import com.bandfeed.chat_service.domain.model.ChatMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatMessageRepository {
    Optional<ChatMessage> findById(UUID id);
    List<ChatMessage> findByChatRoomIdOrderByCreatedAtDesc(UUID chatRoomId, int size);
    ChatMessage save(ChatMessage message);
}
