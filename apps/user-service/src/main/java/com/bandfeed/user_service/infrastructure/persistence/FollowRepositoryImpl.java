package com.bandfeed.user_service.infrastructure.persistence;

import com.bandfeed.user_service.domain.model.Follow;
import com.bandfeed.user_service.domain.repository.FollowRepository;
import com.bandfeed.user_service.infrastructure.entity.FollowEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepository {

    private final FollowJpaRepository jpa;

    @Override
    public Optional<Follow> findByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId) {
        return jpa.findByFollowerIdAndFolloweeId(followerId, followeeId).map(FollowEntity::toDomain);
    }

    @Override
    public List<Follow> findAllByFolloweeId(UUID followeeId) {
        return jpa.findAllByFolloweeId(followeeId).stream().map(FollowEntity::toDomain).toList();
    }

    @Override
    public List<Follow> findAllByFollowerId(UUID followerId) {
        return jpa.findAllByFollowerId(followerId).stream().map(FollowEntity::toDomain).toList();
    }

    @Override
    public Follow save(Follow follow) {
        return jpa.save(FollowEntity.from(follow)).toDomain();
    }

    @Override
    public void delete(Follow follow) {
        jpa.deleteById(follow.getId());
    }

    @Override
    public boolean existsByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId) {
        return jpa.existsByFollowerIdAndFolloweeId(followerId, followeeId);
    }
}
