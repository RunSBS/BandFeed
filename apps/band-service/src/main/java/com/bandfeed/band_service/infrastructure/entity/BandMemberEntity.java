package com.bandfeed.band_service.infrastructure.entity;

import com.bandfeed.band_service.domain.model.BandMember;
import com.bandfeed.band_service.domain.model.BandRole;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "band_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BandMemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bandId;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BandRole role;

    @Builder(access = AccessLevel.PRIVATE)
    private BandMemberEntity(Long id, Long bandId, Long userId, BandRole role) {
        this.id = id;
        this.bandId = bandId;
        this.userId = userId;
        this.role = role;
    }

    public static BandMemberEntity from(BandMember domain) {
        return BandMemberEntity.builder()
                .id(domain.getId())
                .bandId(domain.getBandId())
                .userId(domain.getUserId())
                .role(domain.getRole())
                .build();
    }

    public BandMember toDomain() {
        return BandMember.reconstitute(id, bandId, userId, role, getCreatedAt());
    }

    public void update(BandMember domain) {
        this.role = domain.getRole();
    }
}
