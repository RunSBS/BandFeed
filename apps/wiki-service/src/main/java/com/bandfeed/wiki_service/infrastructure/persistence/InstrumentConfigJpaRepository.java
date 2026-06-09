package com.bandfeed.wiki_service.infrastructure.persistence;

import com.bandfeed.wiki_service.infrastructure.entity.InstrumentConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InstrumentConfigJpaRepository extends JpaRepository<InstrumentConfigEntity, UUID> {
    List<InstrumentConfigEntity> findAllBySongId(UUID songId);
}
