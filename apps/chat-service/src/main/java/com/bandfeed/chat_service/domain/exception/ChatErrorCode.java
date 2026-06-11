package com.bandfeed.chat_service.domain.exception;

import common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode implements ErrorCode {

    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "CH001", "채팅방을 찾을 수 없습니다."),
    NOT_CHAT_PARTICIPANT(HttpStatus.FORBIDDEN, "CH002", "채팅방 참여자만 수행할 수 있습니다."),
    CHAT_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "CH003", "메시지를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
