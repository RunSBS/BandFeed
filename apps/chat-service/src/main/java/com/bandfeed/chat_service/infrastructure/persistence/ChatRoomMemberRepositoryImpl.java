package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.domain.model.ChatRoomMember;
import com.bandfeed.chat_service.domain.repository.ChatRoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberRepositoryImpl implements ChatRoomMemberRepository {

    private final JpaChatRoomMemberRepository jpaChatRoomMemberRepository;

    @Override
    public Optional<ChatRoomMember> findByChatRoomIdAndUserId(Long chatRoomId, Long userId) {
        return jpaChatRoomMemberRepository.findByChatRoomIdAndUserId(chatRoomId, userId);
    }

    @Override
    public List<ChatRoomMember> findAllByChatRoomId(Long chatRoomId) {
        return jpaChatRoomMemberRepository.findAllByChatRoomId(chatRoomId);
    }

    @Override
    public ChatRoomMember save(ChatRoomMember member) {
        return jpaChatRoomMemberRepository.save(member);
    }

    @Override
    public void delete(ChatRoomMember member) {
        jpaChatRoomMemberRepository.delete(member);
    }
}
