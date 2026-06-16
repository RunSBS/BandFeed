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
import common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<CommonResponse<UserResponseDto>> signup(SignupRequestDto request) {
        User user = userService.signup(new CreateUserCommand(request.email(), request.password(), request.nickname(), null));
        return CommonResponse.created("회원가입이 완료되었습니다.", UserResponseDto.from(user));
    }

    @Override
    public ResponseEntity<CommonResponse<LoginResponseDto>> login(LoginRequestDto request) {
        User user = userService.login(request.email(), request.password());
        String accessToken = jwtTokenProvider.createAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        return CommonResponse.ok(new LoginResponseDto(accessToken, refreshToken));
    }

    @Override
    public ResponseEntity<CommonResponse<UserResponseDto>> findMe(UUID userId) {
        return CommonResponse.ok(UserResponseDto.from(userService.findById(userId)));
    }

    @Override
    public ResponseEntity<CommonResponse<UserResponseDto>> findUserById(UUID userId) {
        return CommonResponse.ok(UserResponseDto.from(userService.findById(userId)));
    }

    @Override
    @GetMapping
    public ResponseEntity<CommonResponse<List<UserResponseDto>>> searchUserByNickname(@RequestParam String nickname) {
        List<UserResponseDto> users = userService.searchByNickname(nickname).stream()
                .map(UserResponseDto::from)
                .toList();
        return CommonResponse.ok(users);
    }

    @Override
    @GetMapping("/batch")
    public ResponseEntity<CommonResponse<List<UserResponseDto>>> findUsersByIds(@RequestParam List<UUID> ids) {
        List<UserResponseDto> users = userService.findAllByIds(ids).stream()
                .map(UserResponseDto::from)
                .toList();
        return CommonResponse.ok(users);
    }

    @Override
    public ResponseEntity<CommonResponse<UserResponseDto>> updateProfile(UUID userId, UpdateProfileRequestDto request) {
        User user = userService.updateProfile(userId, request.nickname(), request.profileImageUrl(), request.introduction());
        return CommonResponse.ok(UserResponseDto.from(user));
    }

    @Override
    public ResponseEntity<CommonResponse<?>> changePassword(UUID userId, ChangePasswordRequestDto request) {
        userService.changePassword(userId, request.currentPassword(), request.newPassword());
        return CommonResponse.ok("비밀번호가 변경되었습니다.");
    }

    @Override
    public ResponseEntity<CommonResponse<?>> withdraw(UUID userId) {
        userService.withdraw(userId);
        return CommonResponse.ok("회원 탈퇴가 완료되었습니다.");
    }
}
