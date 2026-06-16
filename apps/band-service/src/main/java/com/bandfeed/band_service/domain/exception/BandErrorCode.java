package com.bandfeed.band_service.domain.exception;

import common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BandErrorCode implements ErrorCode {

    BAND_NOT_FOUND(HttpStatus.NOT_FOUND, "B001", "밴드를 찾을 수 없습니다."),
    NOT_BAND_LEADER(HttpStatus.FORBIDDEN, "B002", "밴드 리더만 수행할 수 있습니다."),
    NOT_BAND_MEMBER(HttpStatus.FORBIDDEN, "B003", "밴드 멤버가 아닙니다."),
    ALREADY_BAND_MEMBER(HttpStatus.CONFLICT, "B004", "이미 밴드에 가입된 멤버입니다."),
    TIMELINE_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "B005", "게시글을 찾을 수 없습니다."),
    NOT_POST_AUTHOR(HttpStatus.FORBIDDEN, "B006", "게시글 작성자만 수행할 수 있습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "B007", "댓글을 찾을 수 없습니다."),
    NOT_COMMENT_AUTHOR(HttpStatus.FORBIDDEN, "B008", "댓글 작성자만 수행할 수 있습니다."),
    INVITATION_NOT_FOUND(HttpStatus.NOT_FOUND, "B009", "초대를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
