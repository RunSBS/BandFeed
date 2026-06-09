package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.model.ChatMessage;
import com.bandfeed.chat_service.domain.repository.ChatMessageRepository;
import com.bandfeed.chat_service.domain.repository.ChatRoomMemberRepository;
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
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    public ChatMessage sendMessage(UUID chatRoomId, UUID senderId, String content) {
        chatRoomService.findRoom(chatRoomId);
        ChatMessage message = ChatMessage.create(chatRoomId, senderId, content);
        return chatMessageRepository.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> findMessages(UUID chatRoomId, int size) {
        return chatMessageRepository.findByChatRoomIdOrderByCreatedAtDesc(chatRoomId, size);
    }

    @Override
    public void readMessage(UUID chatRoomId, UUID userId, UUID messageId) {
        chatRoomMemberRepository.findByChatRoomIdAndUserId(chatRoomId, userId)
                .ifPresent(member -> {
                    member.readMessage(messageId);
                    chatRoomMemberRepository.save(member);
                });
    }
}
