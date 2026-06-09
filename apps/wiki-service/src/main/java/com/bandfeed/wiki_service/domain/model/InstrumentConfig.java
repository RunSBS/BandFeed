package com.bandfeed.wiki_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class InstrumentConfig {

    private final Long id;
    private final Long songId;
    private String instrumentType;
    private String difficulty;
    private String notes;
    private final Long registeredBy;

    @Builder(access = AccessLevel.PRIVATE)
    private InstrumentConfig(Long songId, String instrumentType, String difficulty, String notes, Long registeredBy) {
        this.id = null;
        this.songId = songId;
        this.instrumentType = instrumentType;
        this.difficulty = difficulty;
        this.notes = notes;
        this.registeredBy = registeredBy;
    }

    private InstrumentConfig(Long id, Long songId, String instrumentType, String difficulty, String notes, Long registeredBy) {
        this.id = id;
        this.songId = songId;
        this.instrumentType = instrumentType;
        this.difficulty = difficulty;
        this.notes = notes;
        this.registeredBy = registeredBy;
    }

    public static InstrumentConfig create(Long songId, String instrumentType, String difficulty, String notes, Long registeredBy) {
        return InstrumentConfig.builder()
                .songId(songId)
                .instrumentType(instrumentType)
                .difficulty(difficulty)
                .notes(notes)
                .registeredBy(registeredBy)
                .build();
    }

    public static InstrumentConfig reconstitute(Long id, Long songId, String instrumentType,
                                                String difficulty, String notes, Long registeredBy) {
        return new InstrumentConfig(id, songId, instrumentType, difficulty, notes, registeredBy);
    }

    public void update(String instrumentType, String difficulty, String notes) {
        this.instrumentType = instrumentType;
        this.difficulty = difficulty;
        this.notes = notes;
    }
}
