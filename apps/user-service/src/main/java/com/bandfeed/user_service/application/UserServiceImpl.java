package com.bandfeed.user_service.application;

import com.bandfeed.user_service.application.dto.command.CreateUserCommand;
import com.bandfeed.user_service.domain.exception.DuplicateEmailException;
import com.bandfeed.user_service.domain.exception.InvalidPasswordException;
import com.bandfeed.user_service.domain.exception.UserNotFoundException;
import com.bandfeed.user_service.domain.model.User;
import com.bandfeed.user_service.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signup(CreateUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new DuplicateEmailException(command.email());
        }
        User user = User.create(
                command.email(),
                passwordEncoder.encode(command.password()),
                command.nickname(),
                command.profileImageUrl());
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidPasswordException());

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User updateProfile(UUID userId, String nickname, String profileImageUrl, String introduction) {
        User user = findById(userId);
        user.updateProfile(nickname, profileImageUrl, introduction);
        return userRepository.save(user);
    }

    @Override
    public void changePassword(UUID userId, String oldPassword, String newPassword) {
        User user = findById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        user.changePassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void withdraw(UUID userId) {
        User user = findById(userId);
        userRepository.delete(user);
    }
}
