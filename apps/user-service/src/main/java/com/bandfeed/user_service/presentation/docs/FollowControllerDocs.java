package com.bandfeed.user_service.presentation.docs;

import com.bandfeed.user_service.presentation.dto.request.FollowRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface FollowControllerDocs {

    @PostMapping
    ResponseEntity<?> follow(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody FollowRequestDto request);

    @DeleteMapping("/{followeeId}")
    ResponseEntity<?> unfollow(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID followeeId);

    @GetMapping("/{userId}/followers")
    ResponseEntity<?> findFollowers(@PathVariable UUID userId);

    @GetMapping("/{userId}/followings")
    ResponseEntity<?> findFollowings(@PathVariable UUID userId);
}
