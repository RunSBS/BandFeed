package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.infrastructure.entity.InstrumentConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaInstrumentConfigRepository extends JpaRepository<InstrumentConfigEntity, Long> {
    List<InstrumentConfigEntity> findAllBySongId(Long songId);
}
