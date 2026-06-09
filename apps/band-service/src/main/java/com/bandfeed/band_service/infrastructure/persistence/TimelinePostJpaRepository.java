package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.infrastructure.entity.TimelinePostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TimelinePostJpaRepository extends JpaRepository<TimelinePostEntity, UUID> {
    List<TimelinePostEntity> findAllByBandId(UUID bandId);
}
