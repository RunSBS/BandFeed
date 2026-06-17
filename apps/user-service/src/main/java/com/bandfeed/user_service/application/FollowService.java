package com.bandfeed.user_service.application;

import com.bandfeed.user_service.domain.model.Follow;

import java.util.List;
import java.util.UUID;

public interface FollowService {

    Follow follow(UUID followerId, UUID followeeId);
    void unfollow(UUID followerId, UUID followeeId);
    List<Follow> findFollowers(UUID userId);
    List<Follow> findFollowings(UUID userId);
}
