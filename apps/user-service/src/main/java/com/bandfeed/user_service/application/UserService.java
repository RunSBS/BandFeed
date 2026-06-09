package com.bandfeed.user_service.application;

import com.bandfeed.user_service.application.dto.command.CreateUserCommand;
import com.bandfeed.user_service.domain.model.Follow;
import com.bandfeed.user_service.domain.model.User;

import java.util.List;

public interface UserService {

    // User
    User signup(CreateUserCommand command);
    User login(String email, String rawPassword);
    User findById(Long userId);
    User updateProfile(Long userId, String nickname, String profileImageUrl, String introduction);
    void changePassword(Long userId, String oldPassword, String newPassword);
    void withdraw(Long userId);

    // Follow
    Follow follow(Long followerId, Long followeeId);
    void unfollow(Long followerId, Long followeeId);
    List<Follow> findFollowers(Long userId);
    List<Follow> findFollowings(Long userId);
}
