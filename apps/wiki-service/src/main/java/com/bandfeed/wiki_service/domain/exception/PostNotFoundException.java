package com.bandfeed.wiki_service.domain.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long postId) {
        super("Post not found: " + postId);
    }
}
