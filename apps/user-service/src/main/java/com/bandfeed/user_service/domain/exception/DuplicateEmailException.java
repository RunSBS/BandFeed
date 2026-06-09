package com.bandfeed.user_service.domain.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Duplicate email: " + email);
    }
}
