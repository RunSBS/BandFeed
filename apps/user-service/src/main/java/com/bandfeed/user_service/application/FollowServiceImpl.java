package com.bandfeed.user_service.application;

import com.bandfeed.user_service.domain.exception.DuplicateFollowException;
import com.bandfeed.user_service.domain.exception.FollowNotFoundException;
import com.bandfeed.user_service.domain.exception.SelfFollowException;
import com.bandfeed.user_service.domain.model.Follow;
import com.bandfeed.user_service.domain.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Override
    public Follow follow(UUID followerId, UUID followeeId) {
        if (followerId.equals(followeeId)) {
            throw new SelfFollowException();
        }
        if (followRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId)) {
            throw new DuplicateFollowException(followerId, followeeId);
        }
        Follow follow = Follow.create(followerId, followeeId);
        return followRepository.save(follow);
    }

    @Override
    public void unfollow(UUID followerId, UUID followeeId) {
        Follow follow = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId)
                .orElseThrow(FollowNotFoundException::new);
        followRepository.delete(follow);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Follow> findFollowers(UUID userId) {
        return followRepository.findAllByFolloweeId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Follow> findFollowings(UUID userId) {
        return followRepository.findAllByFollowerId(userId);
    }
}
