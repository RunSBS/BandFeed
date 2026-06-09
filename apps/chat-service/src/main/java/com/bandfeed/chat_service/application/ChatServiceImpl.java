package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.model.ChatMessage;
import com.bandfeed.chat_service.domain.model.ChatRoom;
import com.bandfeed.chat_service.domain.model.ChatRoomMember;
import com.bandfeed.chat_service.domain.repository.ChatMessageRepository;
import com.bandfeed.chat_service.domain.repository.ChatRoomMemberRepository;
import com.bandfeed.chat_service.domain.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    public ChatRoom findOrCreateRoom(Long userAId, Long userBId) {
        return chatRoomRepository.findByParticipants(userAId, userBId)
                .orElseGet(() -> chatRoomRepository.save(ChatRoom.create(userAId, userBId)));
    }

    @Override
    @Transactional(readOnly = true)
    public ChatRoom findRoom(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found: " + roomId));
    }

    @Override
    public ChatMessage sendMessage(Long chatRoomId, Long senderId, String content) {
        findRoom(chatRoomId);
        ChatMessage message = ChatMessage.create(chatRoomId, senderId, content);
        return chatMessageRepository.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> findMessages(Long chatRoomId, Long beforeId, int size) {
        return chatMessageRepository.findByChatRoomIdAndIdLessThanOrderByIdDesc(chatRoomId, beforeId, size);
    }

    @Override
    public void readMessage(Long chatRoomId, Long userId, Long messageId) {
        chatRoomMemberRepository.findByChatRoomIdAndUserId(chatRoomId, userId)
                .ifPresent(member -> {
                    member.readMessage(messageId);
                    chatRoomMemberRepository.save(member);
                });
    }
}
