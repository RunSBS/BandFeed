package com.bandfeed.user_service.application;

import com.bandfeed.user_service.application.dto.command.CreateUserCommand;
import com.bandfeed.user_service.domain.model.Follow;
import com.bandfeed.user_service.domain.model.User;
import com.bandfeed.user_service.domain.repository.FollowRepository;
import com.bandfeed.user_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Override
    public User signup(CreateUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new RuntimeException("Email already exists: " + command.email());
        }
        User user = User.create(command.email(), command.password(), command.nickname(), command.profileImageUrl());
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User login(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    }

    @Override
    public User updateProfile(Long userId, String nickname, String profileImageUrl, String introduction) {
        User user = findById(userId);
        user.updateProfile(nickname, profileImageUrl, introduction);
        return userRepository.save(user);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = findById(userId);
        user.changePassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void withdraw(Long userId) {
        User user = findById(userId);
        userRepository.delete(user);
    }

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
