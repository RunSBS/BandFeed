package com.bandfeed.user_service.domain.exception;

import common.exception.BusinessException;

public class SelfFollowException extends BusinessException {
    public SelfFollowException() {
        super(UserErrorCode.SELF_FOLLOW_NOT_ALLOWED);
    }
}
