package com.bandfeed.user_service.presentation.docs;

import com.bandfeed.user_service.presentation.dto.request.ChangePasswordRequestDto;
import com.bandfeed.user_service.presentation.dto.request.LoginRequestDto;
import com.bandfeed.user_service.presentation.dto.request.SignupRequestDto;
import com.bandfeed.user_service.presentation.dto.request.UpdateProfileRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface UserControllerDocs {

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody SignupRequestDto request);

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequestDto request);

    @GetMapping("/me")
    ResponseEntity<?> findMe(@RequestHeader("X-User-Id") UUID userId);

    @GetMapping("/{userId}")
    ResponseEntity<?> findUserById(@PathVariable UUID userId);

    @PatchMapping("/me")
    ResponseEntity<?> updateProfile(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateProfileRequestDto request);

    @PatchMapping("/me/password")
    ResponseEntity<?> changePassword(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody ChangePasswordRequestDto request);

    @DeleteMapping("/me")
    ResponseEntity<?> withdraw(@RequestHeader("X-User-Id") UUID userId);
}
