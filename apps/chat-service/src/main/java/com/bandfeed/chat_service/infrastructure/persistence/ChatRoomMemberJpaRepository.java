package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.infrastructure.entity.ChatRoomMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberJpaRepository extends JpaRepository<ChatRoomMemberEntity, Long> {
    Optional<ChatRoomMemberEntity> findByChatRoomIdAndUserId(Long chatRoomId, Long userId);
    List<ChatRoomMemberEntity> findAllByChatRoomId(Long chatRoomId);
}
