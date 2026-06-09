package com.bandfeed.user_service.application;

import com.bandfeed.user_service.application.dto.command.CreateUserCommand;
import com.bandfeed.user_service.domain.model.User;

import java.util.UUID;

public interface UserService {

    User signup(CreateUserCommand command);
    User login(String email, String rawPassword);
    User findById(UUID userId);
    User updateProfile(UUID userId, String nickname, String profileImageUrl, String introduction);
    void changePassword(UUID userId, String oldPassword, String newPassword);
    void withdraw(UUID userId);
}
