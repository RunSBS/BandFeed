package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.domain.model.ChatMessage;
import com.bandfeed.chat_service.domain.repository.ChatMessageRepository;
import com.bandfeed.chat_service.infrastructure.entity.ChatMessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageJpaRepository jpa;

    @Override
    public Optional<ChatMessage> findById(UUID id) {
        return jpa.findById(id).map(ChatMessageEntity::toDomain);
    }

    @Override
    public List<ChatMessage> findByChatRoomIdOrderByCreatedAtDesc(UUID chatRoomId, int size) {
        return jpa.findByChatRoomIdOrderByCreatedAtDesc(chatRoomId, PageRequest.of(0, size))
                .stream().map(ChatMessageEntity::toDomain).toList();
    }

    @Override
    public long countUnreadAfter(UUID chatRoomId, UUID userId, LocalDateTime after) {
        return jpa.countUnreadAfter(chatRoomId, userId, after);
    }

    @Override
    public long countAllUnread(UUID chatRoomId, UUID userId) {
        return jpa.countByChatRoomIdAndSenderIdNot(chatRoomId, userId);
    }

    @Override
    public ChatMessage save(ChatMessage message) {
        return jpa.save(ChatMessageEntity.from(message)).toDomain();
    }
}
