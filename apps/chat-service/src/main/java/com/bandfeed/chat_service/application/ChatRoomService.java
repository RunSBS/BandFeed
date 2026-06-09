package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.model.ChatRoom;

public interface ChatRoomService {

    ChatRoom findOrCreateRoom(Long userAId, Long userBId);
    ChatRoom findRoom(Long roomId);
}
