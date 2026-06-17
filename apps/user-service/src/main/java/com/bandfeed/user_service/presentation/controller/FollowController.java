package com.bandfeed.user_service.presentation.controller;

import com.bandfeed.user_service.application.FollowService;
import com.bandfeed.user_service.domain.model.Follow;
import com.bandfeed.user_service.presentation.docs.FollowControllerDocs;
import com.bandfeed.user_service.presentation.dto.request.FollowRequestDto;
import com.bandfeed.user_service.presentation.dto.response.FollowResponseDto;
import common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController implements FollowControllerDocs {

    private final FollowService followService;

    @Override
    public ResponseEntity<CommonResponse<FollowResponseDto>> follow(UUID userId, FollowRequestDto request) {
        Follow follow = followService.follow(userId, request.followeeId());
        return CommonResponse.created("팔로우했습니다.", FollowResponseDto.from(follow));
    }

    @Override
    public ResponseEntity<CommonResponse<?>> unfollow(UUID userId, UUID followeeId) {
        followService.unfollow(userId, followeeId);
        return CommonResponse.ok("언팔로우했습니다.");
    }

    @Override
    public ResponseEntity<CommonResponse<List<FollowResponseDto>>> findFollowers(UUID userId) {
        return CommonResponse.ok(followService.findFollowers(userId).stream().map(FollowResponseDto::from).toList());
    }

    @Override
    public ResponseEntity<CommonResponse<List<FollowResponseDto>>> findFollowings(UUID userId) {
        return CommonResponse.ok(followService.findFollowings(userId).stream().map(FollowResponseDto::from).toList());
    }
}
