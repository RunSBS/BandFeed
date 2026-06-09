package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.TimelinePost;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimelinePostRepository {
    TimelinePost save(TimelinePost post);
    Optional<TimelinePost> findById(UUID id);
    List<TimelinePost> findAllByBandId(UUID bandId);
    void delete(TimelinePost post);
}
