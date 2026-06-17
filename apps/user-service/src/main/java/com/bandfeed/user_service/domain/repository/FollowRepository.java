package com.bandfeed.user_service.domain.repository;

import com.bandfeed.user_service.domain.model.Follow;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowRepository {
    Optional<Follow> findByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);
    List<Follow> findAllByFolloweeId(UUID followeeId);
    List<Follow> findAllByFollowerId(UUID followerId);
    Follow save(Follow follow);
    void delete(Follow follow);
    boolean existsByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);
}
