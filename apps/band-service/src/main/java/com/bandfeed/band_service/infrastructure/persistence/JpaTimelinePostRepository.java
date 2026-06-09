package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.infrastructure.entity.TimelinePostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTimelinePostRepository extends JpaRepository<TimelinePostEntity, Long> {
    List<TimelinePostEntity> findAllByBandId(Long bandId);
}
