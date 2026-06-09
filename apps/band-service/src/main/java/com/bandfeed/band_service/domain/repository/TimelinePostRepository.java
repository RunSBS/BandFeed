package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.TimelinePost;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimelinePostRepository {
    Optional<TimelinePost> findById(UUID id);
    List<TimelinePost> findAllByBandId(UUID bandId);
    TimelinePost save(TimelinePost post);
    void delete(TimelinePost post);
}
