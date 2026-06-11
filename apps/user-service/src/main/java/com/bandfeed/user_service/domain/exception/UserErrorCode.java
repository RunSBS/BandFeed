package com.bandfeed.user_service.domain.exception;

import common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U002", "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "U003", "비밀번호가 일치하지 않습니다."),
    DUPLICATE_FOLLOW(HttpStatus.CONFLICT, "U004", "이미 팔로우한 사용자입니다."),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "U005", "팔로우 관계를 찾을 수 없습니다."),
    SELF_FOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "U006", "자기 자신을 팔로우할 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
