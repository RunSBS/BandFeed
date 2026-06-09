package com.bandfeed.chat_service.domain.repository;

import com.bandfeed.chat_service.domain.model.ChatRoomMember;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRoomMemberRepository {
    Optional<ChatRoomMember> findByChatRoomIdAndUserId(UUID chatRoomId, UUID userId);
    List<ChatRoomMember> findAllByChatRoomId(UUID chatRoomId);
    ChatRoomMember save(ChatRoomMember member);
    void delete(ChatRoomMember member);
}
