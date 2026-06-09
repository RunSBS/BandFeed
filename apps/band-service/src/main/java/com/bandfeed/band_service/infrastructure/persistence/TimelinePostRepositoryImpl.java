package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.TimelinePost;
import com.bandfeed.band_service.domain.repository.TimelinePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TimelinePostRepositoryImpl implements TimelinePostRepository {

    private final JpaTimelinePostRepository jpa;

    @Override
    public Optional<TimelinePost> findById(Long id) {
        return jpa.findById(id);
    }

    @Override
    public List<TimelinePost> findAllByBandId(Long bandId) {
        return jpa.findAllByBandId(bandId);
    }

    @Override
    public TimelinePost save(TimelinePost post) {
        return jpa.save(post);
    }

    @Override
    public void delete(TimelinePost post) {
        jpa.delete(post);
    }
}
