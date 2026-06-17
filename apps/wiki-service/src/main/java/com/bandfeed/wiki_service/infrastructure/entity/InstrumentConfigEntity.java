package com.bandfeed.wiki_service.infrastructure.entity;

import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "instrument_configs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InstrumentConfigEntity extends BaseEntity implements Persistable<UUID> {

    @Transient
    private boolean isNew;

    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID postId;

    @Column(nullable = false)
    private String instrumentType;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID registeredBy;

    private InstrumentConfigEntity(UUID id, UUID postId, String instrumentType, UUID registeredBy, boolean isNew) {
        this.id = id;
        this.postId = postId;
        this.instrumentType = instrumentType;
        this.registeredBy = registeredBy;
        this.isNew = isNew;
    }

    public static InstrumentConfigEntity from(InstrumentConfig domain) {
        return new InstrumentConfigEntity(
                domain.getId(),
                domain.getPostId(),
                domain.getInstrumentType(),
                domain.getRegisteredBy(),
                !domain.isPersisted()
        );
    }

    @Override
    public UUID getId() { return id; }

    @Override
    public boolean isNew() { return isNew; }

    @PostLoad
    @PostPersist
    void markNotNew() { this.isNew = false; }

    public InstrumentConfig toDomain() {
        return InstrumentConfig.reconstitute(id, postId, instrumentType, registeredBy);
    }
}
