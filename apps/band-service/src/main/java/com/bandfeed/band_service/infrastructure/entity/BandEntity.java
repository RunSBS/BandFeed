package com.bandfeed.band_service.infrastructure.entity;

import com.bandfeed.band_service.domain.model.Band;
import com.bandfeed.band_service.domain.model.BandStatus;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bands")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BandEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BandStatus status;

    @Column(nullable = false)
    private Long leaderId;

    @Builder(access = AccessLevel.PRIVATE)
    private BandEntity(Long id, String name, String description, BandStatus status, Long leaderId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.leaderId = leaderId;
    }

    public static BandEntity from(Band domain) {
        return BandEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .status(domain.getStatus())
                .leaderId(domain.getLeaderId())
                .build();
    }

    public Band toDomain() {
        return Band.reconstitute(id, name, description, status, leaderId, getCreatedAt());
    }

    public void update(Band domain) {
        this.name = domain.getName();
        this.description = domain.getDescription();
        this.status = domain.getStatus();
        this.leaderId = domain.getLeaderId();
    }
}
