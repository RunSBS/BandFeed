package com.bandfeed.wiki_service.domain.repository;

import com.bandfeed.wiki_service.domain.model.InstrumentConfig;

import java.util.List;
import java.util.UUID;

public interface InstrumentConfigRepository {
    List<InstrumentConfig> findAllBySongId(UUID songId);
    InstrumentConfig save(InstrumentConfig config);
    void delete(InstrumentConfig config);
}
