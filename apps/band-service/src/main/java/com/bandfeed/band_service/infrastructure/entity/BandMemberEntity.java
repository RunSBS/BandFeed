package com.bandfeed.band_service.infrastructure.entity;

import com.bandfeed.band_service.domain.model.BandMember;
import com.bandfeed.band_service.domain.model.BandRole;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "band_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BandMemberEntity extends BaseEntity implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID bandId;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BandRole role;

    @Builder(access = AccessLevel.PRIVATE)
    private BandMemberEntity(UUID id, UUID bandId, UUID userId, BandRole role, boolean isNew) {
        this.id = id;
        this.isNew = isNew;
        this.bandId = bandId;
        this.userId = userId;
        this.role = role;
    }

    public static BandMemberEntity from(BandMember domain) {
        return BandMemberEntity.builder()
                .id(domain.getId())
                .isNew(!domain.isPersisted())
                .bandId(domain.getBandId())
                .userId(domain.getUserId())
                .role(domain.getRole())
                .build();
    }

    @Override
    public UUID getId() { return id; }

    @Override
    public boolean isNew() { return isNew; }

    @PostLoad
    @PostPersist
    void markNotNew() { this.isNew = false; }

    public BandMember toDomain() {
        return BandMember.reconstitute(id, bandId, userId, role, getCreatedAt());
    }

    public void update(BandMember domain) {
        this.role = domain.getRole();
    }
}
