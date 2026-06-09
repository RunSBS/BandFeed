package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.model.ChatRoom;

import java.util.UUID;

public interface ChatRoomService {

    ChatRoom findOrCreateRoom(UUID userAId, UUID userBId);
    ChatRoom findRoom(UUID roomId);
}
