package com.bandfeed.user_service.application;

import com.bandfeed.user_service.domain.model.Follow;

import java.util.List;

public interface FollowService {

    Follow follow(Long followerId, Long followeeId);
    void unfollow(Long followerId, Long followeeId);
    List<Follow> findFollowers(Long userId);
    List<Follow> findFollowings(Long userId);
}
