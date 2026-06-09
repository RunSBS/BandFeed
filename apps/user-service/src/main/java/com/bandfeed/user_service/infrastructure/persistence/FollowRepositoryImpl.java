package com.bandfeed.user_service.infrastructure.persistence;

import com.bandfeed.user_service.domain.model.Follow;
import com.bandfeed.user_service.domain.repository.FollowRepository;
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
        return jpaFollowRepository.findByFollowerIdAndFolloweeId(followerId, followeeId);
    }

    @Override
    public List<Follow> findAllByFolloweeId(Long followeeId) {
        return jpaFollowRepository.findAllByFolloweeId(followeeId);
    }

    @Override
    public List<Follow> findAllByFollowerId(Long followerId) {
        return jpaFollowRepository.findAllByFollowerId(followerId);
    }

    @Override
    public Follow save(Follow follow) {
        return jpaFollowRepository.save(follow);
    }

    @Override
    public void delete(Follow follow) {
        jpaFollowRepository.delete(follow);
    }

    @Override
    public boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId) {
        return jpaFollowRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId);
    }
}
