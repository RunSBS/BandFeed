package com.bandfeed.wiki_service.domain.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class InstrumentConfig {

    private final UUID id;
    private final boolean persisted;
    private final UUID postId;
    private final String instrumentType;
    private final UUID registeredBy;

    private InstrumentConfig(UUID id, boolean persisted, UUID postId, String instrumentType, UUID registeredBy) {
        this.id = id;
        this.persisted = persisted;
        this.postId = postId;
        this.instrumentType = instrumentType;
        this.registeredBy = registeredBy;
    }

    public static InstrumentConfig create(UUID postId, String instrumentType, UUID registeredBy) {
        return new InstrumentConfig(UUID.randomUUID(), false, postId, instrumentType, registeredBy);
    }

    public static InstrumentConfig reconstitute(UUID id, UUID postId, String instrumentType, UUID registeredBy) {
        return new InstrumentConfig(id, true, postId, instrumentType, registeredBy);
    }
}
