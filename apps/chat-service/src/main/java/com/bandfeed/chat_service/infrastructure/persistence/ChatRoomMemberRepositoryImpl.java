package com.bandfeed.chat_service.infrastructure.persistence;

import com.bandfeed.chat_service.domain.model.ChatRoomMember;
import com.bandfeed.chat_service.domain.repository.ChatRoomMemberRepository;
import com.bandfeed.chat_service.infrastructure.entity.ChatRoomMemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberRepositoryImpl implements ChatRoomMemberRepository {

    private final ChatRoomMemberJpaRepository jpa;

    @Override
    public Optional<ChatRoomMember> findByChatRoomIdAndUserId(Long chatRoomId, Long userId) {
        return jpa.findByChatRoomIdAndUserId(chatRoomId, userId).map(ChatRoomMemberEntity::toDomain);
    }

    @Override
    public List<ChatRoomMember> findAllByChatRoomId(Long chatRoomId) {
        return jpa.findAllByChatRoomId(chatRoomId).stream().map(ChatRoomMemberEntity::toDomain).toList();
    }

    @Override
    public ChatRoomMember save(ChatRoomMember member) {
        return jpa.save(ChatRoomMemberEntity.from(member)).toDomain();
    }

    @Override
    public void delete(ChatRoomMember member) {
        jpa.deleteById(member.getId());
    }
}
