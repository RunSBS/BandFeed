package com.bandfeed.chat_service.domain.repository;

import com.bandfeed.chat_service.domain.model.ChatRoomMember;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository {
    Optional<ChatRoomMember> findByChatRoomIdAndUserId(Long chatRoomId, Long userId);
    List<ChatRoomMember> findAllByChatRoomId(Long chatRoomId);
    ChatRoomMember save(ChatRoomMember member);
    void delete(ChatRoomMember member);
}
