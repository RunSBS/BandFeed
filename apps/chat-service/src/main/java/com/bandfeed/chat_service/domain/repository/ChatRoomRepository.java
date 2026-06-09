package com.bandfeed.chat_service.domain.repository;

import com.bandfeed.chat_service.domain.model.ChatRoom;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository {
    Optional<ChatRoom> findById(UUID id);
    Optional<ChatRoom> findByParticipants(UUID userAId, UUID userBId);
    ChatRoom save(ChatRoom chatRoom);
    void delete(ChatRoom chatRoom);
}
