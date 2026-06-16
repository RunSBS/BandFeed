package com.bandfeed.band_service.presentation.controller;

import com.bandfeed.band_service.application.TimelinePostService;
import com.bandfeed.band_service.domain.model.Comment;
import com.bandfeed.band_service.domain.model.TimelinePost;
import com.bandfeed.band_service.presentation.docs.TimelinePostControllerDocs;
import com.bandfeed.band_service.presentation.dto.request.CreateCommentRequestDto;
import com.bandfeed.band_service.presentation.dto.request.CreateTimelinePostRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateTimelinePostRequestDto;
import com.bandfeed.band_service.presentation.dto.response.CommentResponseDto;
import com.bandfeed.band_service.presentation.dto.response.TimelinePostResponseDto;
import common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/timeline-posts")
@RequiredArgsConstructor
public class TimelinePostController implements TimelinePostControllerDocs {

    private final TimelinePostService timelinePostService;

    @Override
    @PostMapping
    public ResponseEntity<CommonResponse<TimelinePostResponseDto>> createTimelinePost(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateTimelinePostRequestDto request) {
        TimelinePost post = timelinePostService.createTimelinePost(request.bandId(), userId, request.title(), request.content());
        return CommonResponse.created("게시글이 작성되었습니다.", TimelinePostResponseDto.from(post));
    }

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponse<TimelinePostResponseDto>> findTimelinePostById(@PathVariable UUID postId) {
        return CommonResponse.ok(TimelinePostResponseDto.from(timelinePostService.findTimelinePostById(postId)));
    }

    @Override
    @GetMapping
    public ResponseEntity<CommonResponse<Page<TimelinePostResponseDto>>> findTimelinePosts(
            @RequestParam(required = false) UUID bandId,
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestHeader(value = "X-User-Id", required = false) UUID userId) {
        Pageable pageable = PageRequest.of(page, size);
        if ("feed".equalsIgnoreCase(filter)) {
            return CommonResponse.ok(timelinePostService.findFeed(userId, pageable).map(TimelinePostResponseDto::from));
        }
        return CommonResponse.ok(timelinePostService.findAllTimelinePost(bandId, pageable).map(TimelinePostResponseDto::from));
    }

    @Override
    @PatchMapping("/{postId}")
    public ResponseEntity<CommonResponse<TimelinePostResponseDto>> updateTimelinePostInfo(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateTimelinePostRequestDto request) {
        TimelinePost post = timelinePostService.updateTimelinePostInfo(postId, request.title(), request.content(), userId);
        return CommonResponse.ok(TimelinePostResponseDto.from(post));
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponse<?>> deleteTimelinePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId) {
        timelinePostService.deleteTimelinePost(postId, userId);
        return CommonResponse.ok("게시글이 삭제되었습니다.");
    }

    @Override
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommonResponse<CommentResponseDto>> createTimelinePostComment(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateCommentRequestDto request) {
        Comment comment = timelinePostService.createTimelinePostComment(postId, userId, request.content());
        return CommonResponse.created("댓글이 작성되었습니다.", CommentResponseDto.from(comment));
    }

    @Override
    @GetMapping("/{postId}/comments")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> findAllTimelinePostComment(@PathVariable UUID postId) {
        List<CommentResponseDto> comments = timelinePostService.findAllTimelinePostComment(postId).stream()
                .map(CommentResponseDto::from)
                .toList();
        return CommonResponse.ok(comments);
    }

    @Override
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<?>> deleteTimelinePostComment(
            @PathVariable UUID postId,
            @PathVariable UUID commentId,
            @RequestHeader("X-User-Id") UUID userId) {
        timelinePostService.deleteTimelinePostComment(commentId, userId);
        return CommonResponse.ok("댓글이 삭제되었습니다.");
    }
}
