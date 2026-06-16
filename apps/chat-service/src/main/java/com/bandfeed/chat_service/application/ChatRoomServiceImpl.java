package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.exception.ChatRoomNotFoundException;
import com.bandfeed.chat_service.domain.exception.NotChatParticipantException;
import com.bandfeed.chat_service.domain.model.ChatRoom;
import com.bandfeed.chat_service.domain.model.ChatRoomMember;
import com.bandfeed.chat_service.domain.repository.ChatRoomMemberRepository;
import com.bandfeed.chat_service.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    public ChatRoomCreationResult findOrCreateRoom(UUID userAId, UUID userBId) {
        return chatRoomRepository.findByParticipants(userAId, userBId)
                .map(room -> new ChatRoomCreationResult(room, false))
                .orElseGet(() -> {
                    ChatRoom room = chatRoomRepository.save(ChatRoom.create(userAId, userBId));
                    chatRoomMemberRepository.save(ChatRoomMember.create(room.getId(), userAId));
                    chatRoomMemberRepository.save(ChatRoomMember.create(room.getId(), userBId));
                    return new ChatRoomCreationResult(room, true);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public ChatRoom findRoom(UUID roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(ChatRoomNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoom> findMyRooms(UUID userId) {
        return chatRoomRepository.findAllByParticipant(userId);
    }

    @Override
    public void validateParticipant(ChatRoom room, UUID userId) {
        if (!room.getParticipant1Id().equals(userId) && !room.getParticipant2Id().equals(userId)) {
            throw new NotChatParticipantException();
        }
    }
}
