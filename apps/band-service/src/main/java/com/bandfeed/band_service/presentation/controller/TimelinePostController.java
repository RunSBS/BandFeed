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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/timeline-posts")
@RequiredArgsConstructor
public class TimelinePostController implements TimelinePostControllerDocs {

    private final TimelinePostService timelinePostService;

    // ── TimelinePost CRUD ─────────────────────────────────────────────────────

    @Override
    @PostMapping
    public ResponseEntity<?> createTimelinePost(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateTimelinePostRequestDto request) {
        TimelinePost post = timelinePostService.createTimelinePost(request.bandId(), userId, request.title(), request.content());
        return ResponseEntity.status(HttpStatus.CREATED).body(TimelinePostResponseDto.from(post));
    }

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<?> findTimelinePostById(@PathVariable UUID postId) {
        return ResponseEntity.ok(TimelinePostResponseDto.from(timelinePostService.findTimelinePostById(postId)));
    }

    @Override
    @GetMapping
    public ResponseEntity<?> findAllTimelinePost(
            @RequestParam UUID bandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TimelinePostResponseDto> posts = timelinePostService.findAllTimelinePost(bandId, pageable)
                .map(TimelinePostResponseDto::from);
        return ResponseEntity.ok(posts);
    }

    @Override
    @GetMapping("/feed")
    public ResponseEntity<?> findFeed(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TimelinePostResponseDto> posts = timelinePostService.findFeed(userId, pageable)
                .map(TimelinePostResponseDto::from);
        return ResponseEntity.ok(posts);
    }

    @Override
    @PatchMapping("/{postId}")
    public ResponseEntity<?> updateTimelinePostInfo(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateTimelinePostRequestDto request) {
        TimelinePost post = timelinePostService.updateTimelinePostInfo(postId, request.title(), request.content(), userId);
        return ResponseEntity.ok(TimelinePostResponseDto.from(post));
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteTimelinePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId) {
        timelinePostService.deleteTimelinePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    // ── Comment CRUD ──────────────────────────────────────────────────────────

    @Override
    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> createTimelinePostComment(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateCommentRequestDto request) {
        Comment comment = timelinePostService.createTimelinePostComment(postId, userId, request.content());
        return ResponseEntity.status(HttpStatus.CREATED).body(CommentResponseDto.from(comment));
    }

    @Override
    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> findAllTimelinePostComment(@PathVariable UUID postId) {
        List<CommentResponseDto> comments = timelinePostService.findAllTimelinePostComment(postId).stream()
                .map(CommentResponseDto::from)
                .toList();
        return ResponseEntity.ok(comments);
    }

    @Override
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteTimelinePostComment(
            @PathVariable UUID postId,
            @PathVariable UUID commentId,
            @RequestHeader("X-User-Id") UUID userId) {
        timelinePostService.deleteTimelinePostComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
