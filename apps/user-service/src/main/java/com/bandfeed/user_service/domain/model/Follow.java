package com.bandfeed.user_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Follow {

    private final UUID id;
    private final UUID followerId;
    private final UUID followeeId;
    private LocalDateTime followedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Follow(UUID followerId, UUID followeeId) {
        this.id = null;
        this.followerId = followerId;
        this.followeeId = followeeId;
        this.followedAt = null;
    }

    private Follow(UUID id, UUID followerId, UUID followeeId, LocalDateTime followedAt) {
        this.id = id;
        this.followerId = followerId;
        this.followeeId = followeeId;
        this.followedAt = followedAt;
    }

    public static Follow create(UUID followerId, UUID followeeId) {
        return Follow.builder()
                .followerId(followerId)
                .followeeId(followeeId)
                .build();
    }

    public static Follow reconstitute(UUID id, UUID followerId, UUID followeeId, LocalDateTime followedAt) {
        return new Follow(id, followerId, followeeId, followedAt);
    }
}
