package com.bandfeed.user_service.presentation.controller;

import com.bandfeed.user_service.application.FollowService;
import com.bandfeed.user_service.domain.model.Follow;
import com.bandfeed.user_service.presentation.docs.FollowControllerDocs;
import com.bandfeed.user_service.presentation.dto.request.FollowRequestDto;
import com.bandfeed.user_service.presentation.dto.response.FollowResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController implements FollowControllerDocs {

    private final FollowService followService;

    @Override
    public ResponseEntity<?> follow(UUID userId, FollowRequestDto request) {
        Follow follow = followService.follow(userId, request.followeeId());
        return ResponseEntity.status(HttpStatus.CREATED).body(FollowResponseDto.from(follow));
    }

    @Override
    public ResponseEntity<?> unfollow(UUID userId, UUID followeeId) {
        followService.unfollow(userId, followeeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> findFollowers(UUID userId) {
        return ResponseEntity.ok(followService.findFollowers(userId).stream().map(FollowResponseDto::from).toList());
    }

    @Override
    public ResponseEntity<?> findFollowings(UUID userId) {
        return ResponseEntity.ok(followService.findFollowings(userId).stream().map(FollowResponseDto::from).toList());
    }
}
