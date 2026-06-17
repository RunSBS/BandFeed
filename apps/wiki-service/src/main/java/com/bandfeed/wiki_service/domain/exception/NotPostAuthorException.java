package com.bandfeed.wiki_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class NotPostAuthorException extends BusinessException {

    public NotPostAuthorException(UUID userId) {
        super(WikiErrorCode.NOT_POST_AUTHOR);
    }
}
