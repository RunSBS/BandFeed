package com.bandfeed.user_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Follow {

    private final Long id;
    private final Long followerId;
    private final Long followeeId;
    private LocalDateTime followedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Follow(Long followerId, Long followeeId) {
        this.id = null;
        this.followerId = followerId;
        this.followeeId = followeeId;
        this.followedAt = null;
    }

    private Follow(Long id, Long followerId, Long followeeId, LocalDateTime followedAt) {
        this.id = id;
        this.followerId = followerId;
        this.followeeId = followeeId;
        this.followedAt = followedAt;
    }

    public static Follow create(Long followerId, Long followeeId) {
        return Follow.builder()
                .followerId(followerId)
                .followeeId(followeeId)
                .build();
    }

    public static Follow reconstitute(Long id, Long followerId, Long followeeId, LocalDateTime followedAt) {
        return new Follow(id, followerId, followeeId, followedAt);
    }
}
