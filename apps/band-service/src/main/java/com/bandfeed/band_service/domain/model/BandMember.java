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
    private BandMemberStatus status;
    private LocalDateTime joinedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private BandMember(UUID bandId, UUID userId, BandRole role, BandMemberStatus status) {
        this.id = UUID.randomUUID();
        this.persisted = false;
        this.bandId = bandId;
        this.userId = userId;
        this.role = role;
        this.status = status;
        this.joinedAt = null;
    }

    private BandMember(UUID id, UUID bandId, UUID userId, BandRole role, BandMemberStatus status, LocalDateTime joinedAt) {
        this.id = id;
        this.persisted = true;
        this.bandId = bandId;
        this.userId = userId;
        this.role = role;
        this.status = status;
        this.joinedAt = joinedAt;
    }

    public static BandMember create(UUID bandId, UUID userId, BandRole role) {
        return BandMember.builder()
                .bandId(bandId)
                .userId(userId)
                .role(role)
                .status(BandMemberStatus.ACTIVE)
                .build();
    }

    public static BandMember invite(UUID bandId, UUID userId) {
        return BandMember.builder()
                .bandId(bandId)
                .userId(userId)
                .role(BandRole.MEMBER)
                .status(BandMemberStatus.PENDING)
                .build();
    }

    public static BandMember reconstitute(UUID id, UUID bandId, UUID userId, BandRole role, BandMemberStatus status, LocalDateTime joinedAt) {
        return new BandMember(id, bandId, userId, role, status, joinedAt);
    }

    public void accept() {
        this.status = BandMemberStatus.ACTIVE;
    }

    public void promoteToLeader() {
        this.role = BandRole.LEADER;
    }

    public void demoteToMember() {
        this.role = BandRole.MEMBER;
    }

    public boolean isPending() {
        return this.status == BandMemberStatus.PENDING;
    }
}
