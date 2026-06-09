package com.bandfeed.user_service.infrastructure.persistence;

import com.bandfeed.user_service.domain.model.Follow;
import com.bandfeed.user_service.domain.repository.FollowRepository;
import com.bandfeed.user_service.infrastructure.entity.FollowEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepository {

    private final JpaFollowRepository jpaFollowRepository;

    @Override
    public Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId) {
        return jpaFollowRepository.findByFollowerIdAndFolloweeId(followerId, followeeId).map(FollowEntity::toDomain);
    }

    @Override
    public List<Follow> findAllByFolloweeId(Long followeeId) {
        return jpaFollowRepository.findAllByFolloweeId(followeeId).stream().map(FollowEntity::toDomain).toList();
    }

    @Override
    public List<Follow> findAllByFollowerId(Long followerId) {
        return jpaFollowRepository.findAllByFollowerId(followerId).stream().map(FollowEntity::toDomain).toList();
    }

    @Override
    public Follow save(Follow follow) {
        return jpaFollowRepository.save(FollowEntity.from(follow)).toDomain();
    }

    @Override
    public void delete(Follow follow) {
        jpaFollowRepository.deleteById(follow.getId());
    }

    @Override
    public boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId) {
        return jpaFollowRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId);
    }
}
