package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.domain.model.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomIdAndIdLessThanOrderByIdDesc(Long chatRoomId, Long beforeId, Pageable pageable);
}
