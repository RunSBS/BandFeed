package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.domain.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByBandId(Long bandId);
}
