package com.bandfeed.user_service.application;

import com.bandfeed.user_service.application.dto.command.CreateUserCommand;
import com.bandfeed.user_service.domain.model.User;

public interface UserService {

    User signup(CreateUserCommand command);
    User login(String email, String rawPassword);
    User findById(Long userId);
    User updateProfile(Long userId, String nickname, String profileImageUrl, String introduction);
    void changePassword(Long userId, String oldPassword, String newPassword);
    void withdraw(Long userId);
}
