package com.bandfeed.wiki_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Post {

    private final UUID id;
    private final boolean persisted;
    private final UUID songId;
    private final UUID authorId;
    private final UUID bandId;
    private final String bandName;
    private final String bandImageUrl;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Post(UUID songId, UUID authorId, UUID bandId, String bandName, String bandImageUrl, String title, String content) {
        this.id = UUID.randomUUID();
        this.persisted = false;
        this.songId = songId;
        this.authorId = authorId;
        this.bandId = bandId;
        this.bandName = bandName;
        this.bandImageUrl = bandImageUrl;
        this.title = title;
        this.content = content;
        this.createdAt = null;
        this.updatedAt = null;
    }

    private Post(UUID id, UUID songId, UUID authorId, UUID bandId, String bandName, String bandImageUrl,
                 String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.persisted = true;
        this.songId = songId;
        this.authorId = authorId;
        this.bandId = bandId;
        this.bandName = bandName;
        this.bandImageUrl = bandImageUrl;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Post create(UUID songId, UUID authorId, UUID bandId, String bandName, String bandImageUrl,
                              String title, String content) {
        return Post.builder()
                .songId(songId)
                .authorId(authorId)
                .bandId(bandId)
                .bandName(bandName)
                .bandImageUrl(bandImageUrl)
                .title(title)
                .content(content)
                .build();
    }

    public static Post reconstitute(UUID id, UUID songId, UUID authorId, UUID bandId, String bandName,
                                    String bandImageUrl, String title, String content,
                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Post(id, songId, authorId, bandId, bandName, bandImageUrl, title, content, createdAt, updatedAt);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
