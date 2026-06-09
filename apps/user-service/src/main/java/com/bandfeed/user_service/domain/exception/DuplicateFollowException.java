package com.bandfeed.user_service.domain.exception;

public class DuplicateFollowException extends RuntimeException {
    public DuplicateFollowException(Long followerId, Long followeeId) {
        super("Duplicate follow: followerId=" + followerId + ", followeeId=" + followeeId);
    }
}
