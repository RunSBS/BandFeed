package com.bandfeed.user_service.infrastructure.persistence;

import com.bandfeed.user_service.infrastructure.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaFollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<FollowEntity> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    List<FollowEntity> findAllByFolloweeId(Long followeeId);
    List<FollowEntity> findAllByFollowerId(Long followerId);
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
