package com.bandfeed.user_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class DuplicateFollowException extends BusinessException {
    public DuplicateFollowException(UUID followerId, UUID followeeId) {
        super(UserErrorCode.DUPLICATE_FOLLOW);
    }
}
