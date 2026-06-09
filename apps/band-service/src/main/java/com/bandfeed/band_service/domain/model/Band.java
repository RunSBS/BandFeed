package com.bandfeed.band_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Band {

    private final UUID id;
    private final boolean persisted;
    private String name;
    private String description;
    private UUID leaderId;
    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Band(String name, String description, UUID leaderId) {
        this.id = UUID.randomUUID();
        this.persisted = false;
        this.name = name;
        this.description = description;
        this.leaderId = leaderId;
        this.createdAt = null;
    }

    private Band(UUID id, String name, String description, UUID leaderId, LocalDateTime createdAt) {
        this.id = id;
        this.persisted = true;
        this.name = name;
        this.description = description;
        this.leaderId = leaderId;
        this.createdAt = createdAt;
    }

    public static Band create(String name, String description, UUID leaderId) {
        return Band.builder()
                .name(name)
                .description(description)
                .leaderId(leaderId)
                .build();
    }

    public static Band reconstitute(UUID id, String name, String description, UUID leaderId, LocalDateTime createdAt) {
        return new Band(id, name, description, leaderId, createdAt);
    }

    public void transferLeader(UUID newLeaderId) {
        this.leaderId = newLeaderId;
    }

    public void updateInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
