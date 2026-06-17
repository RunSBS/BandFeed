package com.bandfeed.band_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Comment {

    private final UUID id;
    private final boolean persisted;
    private final UUID postId;
    private final UUID authorId;
    private String content;
    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Comment(UUID postId, UUID authorId, String content) {
        this.id = UUID.randomUUID();
        this.persisted = false;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = null;
    }

    private Comment(UUID id, UUID postId, UUID authorId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.persisted = true;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Comment create(UUID postId, UUID authorId, String content) {
        return Comment.builder()
                .postId(postId)
                .authorId(authorId)
                .content(content)
                .build();
    }

    public static Comment reconstitute(UUID id, UUID postId, UUID authorId, String content, LocalDateTime createdAt) {
        return new Comment(id, postId, authorId, content, createdAt);
    }

    public void update(String content) {
        this.content = content;
    }
}
