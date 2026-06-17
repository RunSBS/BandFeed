package com.bandfeed.user_service.presentation.docs;

import com.bandfeed.user_service.presentation.dto.request.FollowRequestDto;
import com.bandfeed.user_service.presentation.dto.response.FollowResponseDto;
import common.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface FollowControllerDocs {

    @PostMapping
    ResponseEntity<CommonResponse<FollowResponseDto>> follow(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody FollowRequestDto request);

    @DeleteMapping("/{followeeId}")
    ResponseEntity<CommonResponse<?>> unfollow(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID followeeId);

    @GetMapping("/{userId}/followers")
    ResponseEntity<CommonResponse<List<FollowResponseDto>>> findFollowers(@PathVariable UUID userId);

    @GetMapping("/{userId}/followings")
    ResponseEntity<CommonResponse<List<FollowResponseDto>>> findFollowings(@PathVariable UUID userId);
}
