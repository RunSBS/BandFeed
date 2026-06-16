package com.bandfeed.band_service.presentation.docs;

import com.bandfeed.band_service.presentation.dto.request.CreateCommentRequestDto;
import com.bandfeed.band_service.presentation.dto.request.CreateTimelinePostRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateTimelinePostRequestDto;
import com.bandfeed.band_service.presentation.dto.response.CommentResponseDto;
import com.bandfeed.band_service.presentation.dto.response.TimelinePostResponseDto;
import common.dto.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface TimelinePostControllerDocs {

    @PostMapping
    ResponseEntity<CommonResponse<TimelinePostResponseDto>> createTimelinePost(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateTimelinePostRequestDto request);

    @GetMapping("/{postId}")
    ResponseEntity<CommonResponse<TimelinePostResponseDto>> findTimelinePostById(@PathVariable UUID postId);

    @GetMapping
    ResponseEntity<CommonResponse<Page<TimelinePostResponseDto>>> findTimelinePosts(
            @RequestParam(required = false) UUID bandId,
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestHeader(value = "X-User-Id", required = false) UUID userId);

    @PatchMapping("/{postId}")
    ResponseEntity<CommonResponse<TimelinePostResponseDto>> updateTimelinePostInfo(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateTimelinePostRequestDto request);

    @DeleteMapping("/{postId}")
    ResponseEntity<CommonResponse<?>> deleteTimelinePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId);

    @PostMapping("/{postId}/comments")
    ResponseEntity<CommonResponse<CommentResponseDto>> createTimelinePostComment(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateCommentRequestDto request);

    @GetMapping("/{postId}/comments")
    ResponseEntity<CommonResponse<List<CommentResponseDto>>> findAllTimelinePostComment(@PathVariable UUID postId);

    @DeleteMapping("/{postId}/comments/{commentId}")
    ResponseEntity<CommonResponse<?>> deleteTimelinePostComment(
            @PathVariable UUID postId,
            @PathVariable UUID commentId,
            @RequestHeader("X-User-Id") UUID userId);
}
