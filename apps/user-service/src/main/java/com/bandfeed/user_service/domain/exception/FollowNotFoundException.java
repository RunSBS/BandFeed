package com.bandfeed.user_service.domain.exception;

import common.exception.BusinessException;

public class FollowNotFoundException extends BusinessException {
    public FollowNotFoundException() {
        super(UserErrorCode.FOLLOW_NOT_FOUND);
    }
}
