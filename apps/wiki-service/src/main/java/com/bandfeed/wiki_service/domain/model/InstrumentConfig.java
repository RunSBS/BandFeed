package com.bandfeed.wiki_service.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class InstrumentConfig {

    private final UUID id;
    private final UUID songId;
    private String instrumentType;
    private String difficulty;
    private String notes;
    private final UUID registeredBy;

    @Builder(access = AccessLevel.PRIVATE)
    private InstrumentConfig(UUID songId, String instrumentType, String difficulty, String notes, UUID registeredBy) {
        this.id = null;
        this.songId = songId;
        this.instrumentType = instrumentType;
        this.difficulty = difficulty;
        this.notes = notes;
        this.registeredBy = registeredBy;
    }

    private InstrumentConfig(UUID id, UUID songId, String instrumentType, String difficulty, String notes, UUID registeredBy) {
        this.id = id;
        this.songId = songId;
        this.instrumentType = instrumentType;
        this.difficulty = difficulty;
        this.notes = notes;
        this.registeredBy = registeredBy;
    }

    public static InstrumentConfig create(UUID songId, String instrumentType, String difficulty, String notes, UUID registeredBy) {
        return InstrumentConfig.builder()
                .songId(songId)
                .instrumentType(instrumentType)
                .difficulty(difficulty)
                .notes(notes)
                .registeredBy(registeredBy)
                .build();
    }

    public static InstrumentConfig reconstitute(UUID id, UUID songId, String instrumentType,
                                                String difficulty, String notes, UUID registeredBy) {
        return new InstrumentConfig(id, songId, instrumentType, difficulty, notes, registeredBy);
    }

    public void update(String instrumentType, String difficulty, String notes) {
        this.instrumentType = instrumentType;
        this.difficulty = difficulty;
        this.notes = notes;
    }
}
