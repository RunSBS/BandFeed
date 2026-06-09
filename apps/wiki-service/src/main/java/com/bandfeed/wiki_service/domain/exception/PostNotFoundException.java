package com.bandfeed.wiki_service.domain.exception;

import java.util.UUID;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(UUID postId) {
        super("Post not found: " + postId);
    }
}
