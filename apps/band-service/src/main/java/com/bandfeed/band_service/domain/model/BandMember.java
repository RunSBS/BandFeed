package com.bandfeed.band_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class BandMember {

    private final UUID id;
    private final boolean persisted;
    private final UUID bandId;
    private final UUID userId;
    private BandRole role;
    private LocalDateTime joinedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private BandMember(UUID bandId, UUID userId, BandRole role) {
        this.id = UUID.randomUUID();
        this.persisted = false;
        this.bandId = bandId;
        this.userId = userId;
        this.role = role;
        this.joinedAt = null;
    }

    private BandMember(UUID id, UUID bandId, UUID userId, BandRole role, LocalDateTime joinedAt) {
        this.id = id;
        this.persisted = true;
        this.bandId = bandId;
        this.userId = userId;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public static BandMember create(UUID bandId, UUID userId, BandRole role) {
        return BandMember.builder()
                .bandId(bandId)
                .userId(userId)
                .role(role)
                .build();
    }

    public static BandMember reconstitute(UUID id, UUID bandId, UUID userId, BandRole role, LocalDateTime joinedAt) {
        return new BandMember(id, bandId, userId, role, joinedAt);
    }

    public void promoteToLeader() {
        this.role = BandRole.LEADER;
    }

    public void demoteToMember() {
        this.role = BandRole.MEMBER;
    }
}
