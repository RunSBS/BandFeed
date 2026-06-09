package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.TimelinePost;

import java.util.List;
import java.util.Optional;

public interface TimelinePostRepository {
    Optional<TimelinePost> findById(Long id);
    List<TimelinePost> findAllByBandId(Long bandId);
    TimelinePost save(TimelinePost post);
    void delete(TimelinePost post);
}
