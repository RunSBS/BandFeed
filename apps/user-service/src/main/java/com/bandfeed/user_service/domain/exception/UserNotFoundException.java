package com.bandfeed.user_service.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("User not found: " + userId);
    }
}
