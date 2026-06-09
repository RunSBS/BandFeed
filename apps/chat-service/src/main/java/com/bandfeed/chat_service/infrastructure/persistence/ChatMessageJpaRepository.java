package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.infrastructure.entity.ChatMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, UUID> {
    List<ChatMessageEntity> findByChatRoomIdOrderByCreatedAtDesc(UUID chatRoomId, Pageable pageable);
}
