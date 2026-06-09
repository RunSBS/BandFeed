package com.bandfeed.wiki_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Post {

    private final UUID id;
    private final UUID songId;
    private final UUID authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Post(UUID songId, UUID authorId, String title, String content) {
        this.id = null;
        this.songId = songId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdAt = null;
        this.updatedAt = null;
    }

    private Post(UUID id, UUID songId, UUID authorId, String title, String content,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.songId = songId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Post create(UUID songId, UUID authorId, String title, String content) {
        return Post.builder()
                .songId(songId)
                .authorId(authorId)
                .title(title)
                .content(content)
                .build();
    }

    public static Post reconstitute(UUID id, UUID songId, UUID authorId, String title, String content,
                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Post(id, songId, authorId, title, content, createdAt, updatedAt);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
