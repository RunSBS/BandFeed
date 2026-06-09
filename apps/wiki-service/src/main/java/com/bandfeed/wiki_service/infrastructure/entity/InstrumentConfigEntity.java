package com.bandfeed.wiki_service.infrastructure.entity;

import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "instrument_configs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InstrumentConfigEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long songId;

    @Column(nullable = false)
    private String instrumentType;

    @Column(nullable = false)
    private String difficulty;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    private Long registeredBy;

    @Builder(access = AccessLevel.PRIVATE)
    private InstrumentConfigEntity(Long id, Long songId, String instrumentType,
                                   String difficulty, String notes, Long registeredBy) {
        this.id = id;
        this.songId = songId;
        this.instrumentType = instrumentType;
        this.difficulty = difficulty;
        this.notes = notes;
        this.registeredBy = registeredBy;
    }

    public static InstrumentConfigEntity from(InstrumentConfig domain) {
        return InstrumentConfigEntity.builder()
                .id(domain.getId())
                .songId(domain.getSongId())
                .instrumentType(domain.getInstrumentType())
                .difficulty(domain.getDifficulty())
                .notes(domain.getNotes())
                .registeredBy(domain.getRegisteredBy())
                .build();
    }

    public InstrumentConfig toDomain() {
        return InstrumentConfig.reconstitute(id, songId, instrumentType, difficulty, notes, registeredBy);
    }

    public void update(InstrumentConfig domain) {
        this.instrumentType = domain.getInstrumentType();
        this.difficulty = domain.getDifficulty();
        this.notes = domain.getNotes();
    }
}
