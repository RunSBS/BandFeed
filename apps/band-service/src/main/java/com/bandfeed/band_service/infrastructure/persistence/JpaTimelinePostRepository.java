package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.TimelinePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTimelinePostRepository extends JpaRepository<TimelinePost, Long> {
    List<TimelinePost> findAllByBandId(Long bandId);
}
