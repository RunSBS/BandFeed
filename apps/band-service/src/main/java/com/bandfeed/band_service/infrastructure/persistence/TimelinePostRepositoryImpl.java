package com.bandfeed.band_service.infrastructure.persistence;

import com.bandfeed.band_service.domain.model.TimelinePost;
import com.bandfeed.band_service.domain.repository.TimelinePostRepository;
import com.bandfeed.band_service.infrastructure.entity.TimelinePostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TimelinePostRepositoryImpl implements TimelinePostRepository {

    private final TimelinePostJpaRepository jpa;

    @Override
    public Optional<TimelinePost> findById(Long id) {
        return jpa.findById(id).map(TimelinePostEntity::toDomain);
    }

    @Override
    public List<TimelinePost> findAllByBandId(Long bandId) {
        return jpa.findAllByBandId(bandId).stream().map(TimelinePostEntity::toDomain).toList();
    }

    @Override
    public TimelinePost save(TimelinePost post) {
        return jpa.save(TimelinePostEntity.from(post)).toDomain();
    }

    @Override
    public void delete(TimelinePost post) {
        jpa.deleteById(post.getId());
    }
}
