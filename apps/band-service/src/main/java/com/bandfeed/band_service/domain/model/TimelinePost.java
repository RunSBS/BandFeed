package com.bandfeed.band_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TimelinePost {

    private final UUID id;
    private final boolean persisted;
    private final UUID bandId;
    private final UUID authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private TimelinePost(UUID bandId, UUID authorId, String title, String content) {
        this.id = UUID.randomUUID();
        this.persisted = false;
        this.bandId = bandId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdAt = null;
        this.updatedAt = null;
    }

    private TimelinePost(UUID id, UUID bandId, UUID authorId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.persisted = true;
        this.bandId = bandId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static TimelinePost create(UUID bandId, UUID authorId, String title, String content) {
        return TimelinePost.builder()
                .bandId(bandId)
                .authorId(authorId)
                .title(title)
                .content(content)
                .build();
    }

    public static TimelinePost reconstitute(UUID id, UUID bandId, UUID authorId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new TimelinePost(id, bandId, authorId, title, content, createdAt, updatedAt);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
