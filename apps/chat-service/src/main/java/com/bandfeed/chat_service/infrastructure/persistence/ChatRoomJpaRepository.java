package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.infrastructure.entity.ChatRoomEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity, UUID> {
    Optional<ChatRoomEntity> findByParticipant1IdAndParticipant2Id(UUID participant1Id, UUID participant2Id);
    List<ChatRoomEntity> findAllByParticipant1IdOrParticipant2Id(UUID participant1Id, UUID participant2Id, Sort sort);
}
