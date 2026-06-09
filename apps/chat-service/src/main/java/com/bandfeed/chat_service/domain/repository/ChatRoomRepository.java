package com.bandfeed.chat_service.domain.repository;

import com.bandfeed.chat_service.domain.model.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository {
    Optional<ChatRoom> findById(Long id);
    Optional<ChatRoom> findByParticipants(Long userAId, Long userBId);
    ChatRoom save(ChatRoom chatRoom);
    void delete(ChatRoom chatRoom);
}
