package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.TimelinePost;
import com.bandfeed.band_service.domain.repository.TimelinePostRepository;
import com.bandfeed.band_service.infrastructure.entity.TimelinePostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TimelinePostRepositoryImpl implements TimelinePostRepository {

    private final TimelinePostJpaRepository jpa;

    @Override
    public TimelinePost save(TimelinePost post) {
        return jpa.save(TimelinePostEntity.from(post)).toDomain();
    }

    @Override
    public Optional<TimelinePost> findById(UUID id) {
        return jpa.findById(id).map(TimelinePostEntity::toDomain);
    }

    @Override
    public Page<TimelinePost> findAllByBandId(UUID bandId, Pageable pageable) {
        return jpa.findAllByBandId(bandId, pageable).map(TimelinePostEntity::toDomain);
    }

    @Override
    public Page<TimelinePost> findAllByBandIdIn(List<UUID> bandIds, Pageable pageable) {
        return jpa.findAllByBandIdIn(bandIds, pageable).map(TimelinePostEntity::toDomain);
    }

    @Override
    public void delete(TimelinePost post) {
        jpa.deleteById(post.getId());
    }
}
