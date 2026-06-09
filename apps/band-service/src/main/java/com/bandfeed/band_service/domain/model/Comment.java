package com.bandfeed.band_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Comment {

    private final Long id;
    private final Long postId;
    private final Long authorId;
    private String content;
    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Comment(Long postId, Long authorId, String content) {
        this.id = null;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = null;
    }

    private Comment(Long id, Long postId, Long authorId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Comment create(Long postId, Long authorId, String content) {
        return Comment.builder()
                .postId(postId)
                .authorId(authorId)
                .content(content)
                .build();
    }

    public static Comment reconstitute(Long id, Long postId, Long authorId, String content, LocalDateTime createdAt) {
        return new Comment(id, postId, authorId, content, createdAt);
    }

    public void update(String content) {
        this.content = content;
    }
}
