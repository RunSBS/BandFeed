package com.bandfeed.wiki_service.domain.repository;

import com.bandfeed.wiki_service.domain.model.InstrumentConfig;

import java.util.List;

public interface InstrumentConfigRepository {
    List<InstrumentConfig> findAllBySongId(Long songId);
    InstrumentConfig save(InstrumentConfig config);
    void delete(InstrumentConfig config);
}
