package com.bandfeed.user_service.presentation.controller;

import com.bandfeed.user_service.application.UserService;
import com.bandfeed.user_service.application.dto.command.CreateUserCommand;
import com.bandfeed.user_service.domain.model.User;
import com.bandfeed.user_service.infrastructure.security.JwtTokenProvider;
import com.bandfeed.user_service.presentation.docs.UserControllerDocs;
import com.bandfeed.user_service.presentation.dto.request.ChangePasswordRequestDto;
import com.bandfeed.user_service.presentation.dto.request.LoginRequestDto;
import com.bandfeed.user_service.presentation.dto.request.SignupRequestDto;
import com.bandfeed.user_service.presentation.dto.request.UpdateProfileRequestDto;
import com.bandfeed.user_service.presentation.dto.response.LoginResponseDto;
import com.bandfeed.user_service.presentation.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<?> signup(SignupRequestDto request) {
        User user = userService.signup(new CreateUserCommand(request.email(), request.password(), request.nickname(), null));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDto.from(user));
    }

    @Override
    public ResponseEntity<?> login(LoginRequestDto request) {
        User user = userService.login(request.email(), request.password());
        String accessToken = jwtTokenProvider.createAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        return ResponseEntity.ok(new LoginResponseDto(accessToken, refreshToken));
    }

    @Override
    public ResponseEntity<?> findMe(UUID userId) {
        return ResponseEntity.ok(UserResponseDto.from(userService.findById(userId)));
    }

    @Override
    public ResponseEntity<?> findUserById(UUID userId) {
        return ResponseEntity.ok(UserResponseDto.from(userService.findById(userId)));
    }

    @Override
    public ResponseEntity<?> updateProfile(UUID userId, UpdateProfileRequestDto request) {
        User user = userService.updateProfile(userId, request.nickname(), request.profileImageUrl(), request.introduction());
        return ResponseEntity.ok(UserResponseDto.from(user));
    }

    @Override
    public ResponseEntity<?> changePassword(UUID userId, ChangePasswordRequestDto request) {
        userService.changePassword(userId, request.currentPassword(), request.newPassword());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> withdraw(UUID userId) {
        userService.withdraw(userId);
        return ResponseEntity.noContent().build();
    }
}
