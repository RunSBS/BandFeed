package com.bandfeed.chat_service.domain.exception;

import common.exception.BusinessException;

public class ChatMessageNotFoundException extends BusinessException {

    public ChatMessageNotFoundException() {
        super(ChatErrorCode.CHAT_MESSAGE_NOT_FOUND);
    }
}
