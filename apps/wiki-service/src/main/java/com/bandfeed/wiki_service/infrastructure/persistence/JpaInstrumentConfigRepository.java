package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.domain.model.InstrumentConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaInstrumentConfigRepository extends JpaRepository<InstrumentConfig, Long> {
    List<InstrumentConfig> findAllBySongId(Long songId);
}
