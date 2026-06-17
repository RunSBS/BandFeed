package com.bandfeed.band_service.domain.repository;

import com.bandfeed.band_service.domain.model.TimelinePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TimelinePostRepository {
    TimelinePost save(TimelinePost post);
    Optional<TimelinePost> findById(UUID id);
    Page<TimelinePost> findAllByBandId(UUID bandId, Pageable pageable);
    Page<TimelinePost> findAllByBandIdIn(List<UUID> bandIds, Pageable pageable);
    void delete(TimelinePost post);
}
