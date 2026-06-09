package com.bandfeed.user_service.infrastructure.persistence;

import com.bandfeed.user_service.infrastructure.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, UUID> {
    Optional<FollowEntity> findByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);
    List<FollowEntity> findAllByFolloweeId(UUID followeeId);
    List<FollowEntity> findAllByFollowerId(UUID followerId);
    boolean existsByFollowerIdAndFolloweeId(UUID followerId, UUID followeeId);
}
