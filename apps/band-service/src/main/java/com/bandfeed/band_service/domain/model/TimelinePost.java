package com.bandfeed.band_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimelinePost {

    private final Long id;
    private final Long bandId;
    private final Long authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private TimelinePost(Long bandId, Long authorId, String title, String content) {
        this.id = null;
        this.bandId = bandId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdAt = null;
        this.updatedAt = null;
    }

    private TimelinePost(Long id, Long bandId, Long authorId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.bandId = bandId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static TimelinePost create(Long bandId, Long authorId, String title, String content) {
        return TimelinePost.builder()
                .bandId(bandId)
                .authorId(authorId)
                .title(title)
                .content(content)
                .build();
    }

    public static TimelinePost reconstitute(Long id, Long bandId, Long authorId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new TimelinePost(id, bandId, authorId, title, content, createdAt, updatedAt);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
