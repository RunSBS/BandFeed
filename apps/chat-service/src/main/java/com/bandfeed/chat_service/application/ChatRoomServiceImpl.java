package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.model.ChatRoom;
import com.bandfeed.chat_service.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoom findOrCreateRoom(UUID userAId, UUID userBId) {
        return chatRoomRepository.findByParticipants(userAId, userBId)
                .orElseGet(() -> chatRoomRepository.save(ChatRoom.create(userAId, userBId)));
    }

    @Override
    @Transactional(readOnly = true)
    public ChatRoom findRoom(UUID roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found: " + roomId));
    }
}
