package com.bandfeed.band_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BandMember {

    private final Long id;
    private final Long bandId;
    private final Long userId;
    private BandRole role;
    private LocalDateTime joinedAt;

    @Builder(access = AccessLevel.PRIVATE)
    private BandMember(Long bandId, Long userId, BandRole role) {
        this.id = null;
        this.bandId = bandId;
        this.userId = userId;
        this.role = role;
        this.joinedAt = null;
    }

    private BandMember(Long id, Long bandId, Long userId, BandRole role, LocalDateTime joinedAt) {
        this.id = id;
        this.bandId = bandId;
        this.userId = userId;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public static BandMember create(Long bandId, Long userId, BandRole role) {
        return BandMember.builder()
                .bandId(bandId)
                .userId(userId)
                .role(role)
                .build();
    }

    public static BandMember reconstitute(Long id, Long bandId, Long userId, BandRole role, LocalDateTime joinedAt) {
        return new BandMember(id, bandId, userId, role, joinedAt);
    }

    public void promoteToLeader() {
        this.role = BandRole.LEADER;
    }

    public void demoteToMember() {
        this.role = BandRole.MEMBER;
    }
}
