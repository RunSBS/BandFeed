package com.bandfeed.chat_service.domain.exception;

import common.exception.BusinessException;

public class ChatRoomNotFoundException extends BusinessException {

    public ChatRoomNotFoundException() {
        super(ChatErrorCode.CHAT_ROOM_NOT_FOUND);
    }
}
