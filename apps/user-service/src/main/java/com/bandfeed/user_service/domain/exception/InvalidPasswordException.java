package com.bandfeed.user_service.domain.exception;

import common.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException() {
        super(UserErrorCode.INVALID_PASSWORD);
    }
}
