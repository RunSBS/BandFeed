package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.domain.model.ChatRoom;
import com.bandfeed.chat_service.domain.repository.ChatRoomRepository;
import com.bandfeed.chat_service.infrastructure.entity.ChatRoomEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final ChatRoomJpaRepository jpa;

    @Override
    public Optional<ChatRoom> findById(UUID id) {
        return jpa.findById(id).map(ChatRoomEntity::toDomain);
    }

    @Override
    public Optional<ChatRoom> findByParticipants(UUID userAId, UUID userBId) {
        UUID p1 = userAId.compareTo(userBId) < 0 ? userAId : userBId;
        UUID p2 = userAId.compareTo(userBId) < 0 ? userBId : userAId;
        return jpa.findByParticipant1IdAndParticipant2Id(p1, p2).map(ChatRoomEntity::toDomain);
    }

    @Override
    public List<ChatRoom> findAllByParticipant(UUID userId) {
        return jpa.findAllByParticipant1IdOrParticipant2Id(userId, userId, Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream().map(ChatRoomEntity::toDomain).toList();
    }

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return jpa.save(ChatRoomEntity.from(chatRoom)).toDomain();
    }

    @Override
    public void delete(ChatRoom chatRoom) {
        jpa.deleteById(chatRoom.getId());
    }
}
