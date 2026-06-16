package com.bandfeed.chat_service.application;

import com.bandfeed.chat_service.domain.model.ChatRoom;

public record ChatRoomCreationResult(ChatRoom room, boolean created) {}
