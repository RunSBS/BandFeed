package com.bandfeed.chat_service.domain.repository;

import com.bandfeed.chat_service.domain.model.ChatMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatMessageRepository {
    Optional<ChatMessage> findById(UUID id);
    List<ChatMessage> findByChatRoomIdOrderByCreatedAtDesc(UUID chatRoomId, int size);
    long countUnreadAfter(UUID chatRoomId, UUID userId, LocalDateTime after);
    long countAllUnread(UUID chatRoomId, UUID userId);
    ChatMessage save(ChatMessage message);
}
