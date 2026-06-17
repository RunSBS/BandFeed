package com.bandfeed.band_service.infrastructure.entity;

import com.bandfeed.band_service.domain.model.Band;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "bands")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BandEntity extends BaseEntity implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID leaderId;

    @Builder(access = AccessLevel.PRIVATE)
    private BandEntity(UUID id, String name, String description, UUID leaderId, boolean isNew) {
        this.id = id;
        this.isNew = isNew;
        this.name = name;
        this.description = description;
        this.leaderId = leaderId;
    }

    public static BandEntity from(Band domain) {
        return BandEntity.builder()
                .id(domain.getId())
                .isNew(!domain.isPersisted())
                .name(domain.getName())
                .description(domain.getDescription())
                .leaderId(domain.getLeaderId())
                .build();
    }

    @Override
    public UUID getId() { return id; }

    @Override
    public boolean isNew() { return isNew; }

    @PostLoad
    @PostPersist
    void markNotNew() { this.isNew = false; }

    public Band toDomain() {
        return Band.reconstitute(id, name, description, leaderId, getCreatedAt());
    }

    public void update(Band domain) {
        this.name = domain.getName();
        this.description = domain.getDescription();
        this.leaderId = domain.getLeaderId();
    }
}
