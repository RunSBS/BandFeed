package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.infrastructure.entity.ChatRoomMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRoomMemberJpaRepository extends JpaRepository<ChatRoomMemberEntity, UUID> {
    Optional<ChatRoomMemberEntity> findByChatRoomIdAndUserId(UUID chatRoomId, UUID userId);
    List<ChatRoomMemberEntity> findAllByChatRoomId(UUID chatRoomId);
    List<ChatRoomMemberEntity> findAllByUserId(UUID userId);
}
