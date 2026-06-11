package com.bandfeed.wiki_service.domain.exception;

import common.exception.BusinessException;

import java.util.UUID;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException(UUID postId) {
        super(WikiErrorCode.POST_NOT_FOUND);
    }
}
