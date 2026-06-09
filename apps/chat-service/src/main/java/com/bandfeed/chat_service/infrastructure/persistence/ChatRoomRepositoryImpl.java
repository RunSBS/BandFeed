package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.domain.model.ChatRoom;
import com.bandfeed.chat_service.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final JpaChatRoomRepository jpaChatRoomRepository;

    @Override
    public Optional<ChatRoom> findById(Long id) {
        return jpaChatRoomRepository.findById(id);
    }

    @Override
    public Optional<ChatRoom> findByBandId(Long bandId) {
        return jpaChatRoomRepository.findByBandId(bandId);
    }

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return jpaChatRoomRepository.save(chatRoom);
    }

    @Override
    public void delete(ChatRoom chatRoom) {
        jpaChatRoomRepository.delete(chatRoom);
    }
}
