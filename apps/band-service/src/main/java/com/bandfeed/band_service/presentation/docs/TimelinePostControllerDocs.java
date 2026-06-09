package com.bandfeed.band_service.presentation.docs;

import com.bandfeed.band_service.presentation.dto.request.CreateCommentRequestDto;
import com.bandfeed.band_service.presentation.dto.request.CreateTimelinePostRequestDto;
import com.bandfeed.band_service.presentation.dto.request.UpdateTimelinePostRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface TimelinePostControllerDocs {

    // ── TimelinePost CRUD ─────────────────────────────────────────────────────

    @PostMapping
    ResponseEntity<?> createTimelinePost(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateTimelinePostRequestDto request);

    @GetMapping("/{postId}")
    ResponseEntity<?> findTimelinePostById(@PathVariable UUID postId);

    @GetMapping
    ResponseEntity<?> findAllTimelinePost(
            @RequestParam UUID bandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size);

    @PatchMapping("/{postId}")
    ResponseEntity<?> updateTimelinePostInfo(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdateTimelinePostRequestDto request);

    @DeleteMapping("/{postId}")
    ResponseEntity<?> deleteTimelinePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId);

    // ── Comment CRUD ──────────────────────────────────────────────────────────

    @PostMapping("/{postId}/comments")
    ResponseEntity<?> createTimelinePostComment(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateCommentRequestDto request);

    @DeleteMapping("/{postId}/comments/{commentId}")
    ResponseEntity<?> deleteTimelinePostComment(
            @PathVariable UUID postId,
            @PathVariable UUID commentId,
            @RequestHeader("X-User-Id") UUID userId);
}
