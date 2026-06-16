package com.bandfeed.user_service.presentation.docs;

import com.bandfeed.user_service.presentation.dto.request.ChangePasswordRequestDto;
import com.bandfeed.user_service.presentation.dto.request.LoginRequestDto;
import com.bandfeed.user_service.presentation.dto.request.SignupRequestDto;
import com.bandfeed.user_service.presentation.dto.request.UpdateProfileRequestDto;
import com.bandfeed.user_service.presentation.dto.response.LoginResponseDto;
import com.bandfeed.user_service.presentation.dto.response.UserResponseDto;
import common.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface UserControllerDocs {

    @PostMapping("/signup")
    ResponseEntity<CommonResponse<UserResponseDto>> signup(@RequestBody SignupRequestDto request);

    @PostMapping("/login")
    ResponseEntity<CommonResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto request);

    @GetMapping("/me")
    ResponseEntity<CommonResponse<UserResponseDto>> findMe(@RequestHeader("X-User-Id") UUID userId);

    @GetMapping("/{userId}")
    ResponseEntity<CommonResponse<UserResponseDto>> findUserById(@PathVariable UUID userId);

    @GetMapping
    ResponseEntity<CommonResponse<List<UserResponseDto>>> searchUserByNickname(@RequestParam String nickname);

    @GetMapping("/batch")
    ResponseEntity<CommonResponse<List<UserResponseDto>>> findUsersByIds(@RequestParam List<UUID> ids);

    @PatchMapping("/me")
    ResponseEntity<CommonResponse<UserResponseDto>> updateProfile(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateProfileRequestDto request);

    @PatchMapping("/me/password")
    ResponseEntity<CommonResponse<?>> changePassword(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody ChangePasswordRequestDto request);

    @DeleteMapping("/me")
    ResponseEntity<CommonResponse<?>> withdraw(@RequestHeader("X-User-Id") UUID userId);
}
