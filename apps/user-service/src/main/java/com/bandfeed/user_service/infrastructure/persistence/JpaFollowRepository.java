package com.bandfeed.user_service.infrastructure.persistence;

import com.bandfeed.user_service.domain.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaFollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    List<Follow> findAllByFolloweeId(Long followeeId);
    List<Follow> findAllByFollowerId(Long followerId);
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
