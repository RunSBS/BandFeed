package com.bandfeed.band_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Band {

    private final Long id;
    private String name;
    private String description;
    private BandStatus status;
    private Long leaderId;
    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private Band(String name, String description, Long leaderId) {
        this.id = null;
        this.name = name;
        this.description = description;
        this.status = BandStatus.ACTIVE;
        this.leaderId = leaderId;
        this.createdAt = null;
    }

    private Band(Long id, String name, String description, BandStatus status, Long leaderId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.leaderId = leaderId;
        this.createdAt = createdAt;
    }

    public static Band create(String name, String description, Long leaderId) {
        return Band.builder()
                .name(name)
                .description(description)
                .leaderId(leaderId)
                .build();
    }

    public static Band reconstitute(Long id, String name, String description, BandStatus status, Long leaderId, LocalDateTime createdAt) {
        return new Band(id, name, description, status, leaderId, createdAt);
    }

    public void disband() {
        this.status = BandStatus.DISBANDED;
    }

    public void transferLeader(Long newLeaderId) {
        this.leaderId = newLeaderId;
    }

    public void updateInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
