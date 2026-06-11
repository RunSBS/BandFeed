package com.bandfeed.user_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(UUID userId) {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
