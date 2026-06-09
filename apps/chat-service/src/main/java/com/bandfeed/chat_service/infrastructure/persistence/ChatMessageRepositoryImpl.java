package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.domain.model.ChatMessage;
import com.bandfeed.chat_service.domain.repository.ChatMessageRepository;
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
        return jpaChatMessageRepository.findById(id);
    }

    @Override
    public List<ChatMessage> findByChatRoomIdAndIdLessThanOrderByIdDesc(Long chatRoomId, Long beforeId, int size) {
        return jpaChatMessageRepository.findByChatRoomIdAndIdLessThanOrderByIdDesc(chatRoomId, beforeId, PageRequest.of(0, size));
    }

    @Override
    public ChatMessage save(ChatMessage message) {
        return jpaChatMessageRepository.save(message);
    }
}
