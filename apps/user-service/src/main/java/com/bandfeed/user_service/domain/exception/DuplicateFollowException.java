package com.bandfeed.user_service.domain.exception;

import java.util.UUID;

public class DuplicateFollowException extends RuntimeException {
    public DuplicateFollowException(UUID followerId, UUID followeeId) {
        super("Duplicate follow: followerId=" + followerId + ", followeeId=" + followeeId);
    }
}
