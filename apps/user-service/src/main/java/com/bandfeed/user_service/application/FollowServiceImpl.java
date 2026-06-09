package com.bandfeed.user_service.application;

import com.bandfeed.user_service.domain.model.Follow;
import com.bandfeed.user_service.domain.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Override
    public Follow follow(Long followerId, Long followeeId) {
        if (followRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
            throw new RuntimeException("Already following");
        }
        Follow follow = Follow.create(followerId, followeeId);
        return followRepository.save(follow);
    }

    @Override
    public void unfollow(Long followerId, Long followeeId) {
        Follow follow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                .orElseThrow(() -> new RuntimeException("Follow not found"));
        followRepository.delete(follow);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Follow> findFollowers(Long userId) {
        return followRepository.findAllByFolloweeId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Follow> findFollowings(Long userId) {
        return followRepository.findAllByFollowerId(userId);
    }
}
