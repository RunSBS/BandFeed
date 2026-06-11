package com.bandfeed.wiki_service.domain.exception;

import common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WikiErrorCode implements ErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "W001", "게시글을 찾을 수 없습니다."),
    NOT_POST_AUTHOR(HttpStatus.FORBIDDEN, "W002", "게시글 작성자만 수행할 수 있습니다."),
    SONG_NOT_FOUND(HttpStatus.NOT_FOUND, "W003", "곡을 찾을 수 없습니다."),
    DUPLICATE_SONG(HttpStatus.CONFLICT, "W004", "이미 등록된 곡입니다."),
    INSTRUMENT_CONFIG_NOT_FOUND(HttpStatus.NOT_FOUND, "W005", "악기 설정을 찾을 수 없습니다."),
    SPOTIFY_API_ERROR(HttpStatus.BAD_GATEWAY, "W006", "음원 정보를 불러오지 못했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
