package com.bandfeed.user_service.domain.repository;

import com.bandfeed.user_service.domain.model.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowRepository {
    Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    List<Follow> findAllByFolloweeId(Long followeeId);
    List<Follow> findAllByFollowerId(Long followerId);
    Follow save(Follow follow);
    void delete(Follow follow);
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
