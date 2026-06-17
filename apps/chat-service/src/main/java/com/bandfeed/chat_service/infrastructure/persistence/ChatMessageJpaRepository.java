package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.infrastructure.entity.ChatMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, UUID> {
    List<ChatMessageEntity> findByChatRoomIdOrderByCreatedAtDesc(UUID chatRoomId, Pageable pageable);

    @Query("SELECT COUNT(m) FROM ChatMessageEntity m WHERE m.chatRoomId = :roomId AND m.senderId != :userId AND m.createdAt > :after")
    long countUnreadAfter(@Param("roomId") UUID roomId, @Param("userId") UUID userId, @Param("after") LocalDateTime after);

    long countByChatRoomIdAndSenderIdNot(UUID chatRoomId, UUID senderId);
}
