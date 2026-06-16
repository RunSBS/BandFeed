package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.model.ChatRoom;

import java.util.List;
import java.util.UUID;

public interface ChatRoomService {

    ChatRoomCreationResult findOrCreateRoom(UUID userAId, UUID userBId);
    ChatRoom findRoom(UUID roomId);
    List<ChatRoom> findMyRooms(UUID userId);
    void validateParticipant(ChatRoom room, UUID userId);
}
