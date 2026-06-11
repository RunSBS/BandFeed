package com.bandfeed.wiki_service.presentation.docs;

import com.bandfeed.wiki_service.presentation.dto.request.CreatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.UpdatePostRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface PostControllerDocs {

    // ── WikiPost CRUD ─────────────────────────────────────────────────────────

    @PostMapping
    ResponseEntity<?> createPost(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreatePostRequestDto request);

    @GetMapping("/{postId}")
    ResponseEntity<?> findPostById(@PathVariable UUID postId);

    @GetMapping
    ResponseEntity<?> findAllPostsBySong(@RequestParam UUID songId);

    @PatchMapping("/{postId}")
    ResponseEntity<?> updatePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdatePostRequestDto request);

    @DeleteMapping("/{postId}")
    ResponseEntity<?> deletePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId);
}
