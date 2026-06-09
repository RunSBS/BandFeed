package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.domain.model.ChatMessage;
import com.bandfeed.chat_service.domain.repository.ChatMessageRepository;
import com.bandfeed.chat_service.infrastructure.entity.ChatMessageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final JpaChatMessageRepository jpaChatMessageRepository;

    @Override
    public Optional<ChatMessage> findById(Long id) {
        return jpaChatMessageRepository.findById(id).map(ChatMessageEntity::toDomain);
    }

    @Override
    public List<ChatMessage> findByChatRoomIdAndIdLessThanOrderByIdDesc(Long chatRoomId, Long beforeId, int size) {
        return jpaChatMessageRepository
                .findByChatRoomIdAndIdLessThanOrderByIdDesc(chatRoomId, beforeId, PageRequest.of(0, size))
                .stream().map(ChatMessageEntity::toDomain).toList();
    }

    @Override
    public ChatMessage save(ChatMessage message) {
        return jpaChatMessageRepository.save(ChatMessageEntity.from(message)).toDomain();
    }
}
