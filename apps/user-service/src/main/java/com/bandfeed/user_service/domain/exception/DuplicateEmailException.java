package com.bandfeed.user_service.domain.exception;

import common.exception.BusinessException;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException(String email) {
        super(UserErrorCode.DUPLICATE_EMAIL);
    }
}
