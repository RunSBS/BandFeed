package com.bandfeed.chat_service.domain.exception;

import common.exception.BusinessException;

public class NotChatParticipantException extends BusinessException {

    public NotChatParticipantException() {
        super(ChatErrorCode.NOT_CHAT_PARTICIPANT);
    }
}
