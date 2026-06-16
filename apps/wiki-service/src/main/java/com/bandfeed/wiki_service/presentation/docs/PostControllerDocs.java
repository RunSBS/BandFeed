package com.bandfeed.wiki_service.presentation.docs;

import com.bandfeed.wiki_service.presentation.dto.request.AddInstrumentConfigRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.CreatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.UpdatePostRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface PostControllerDocs {

    @PostMapping
    ResponseEntity<?> createPost(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreatePostRequestDto request);

    @GetMapping("/{postId}")
    ResponseEntity<?> findPostById(@PathVariable UUID postId);

    @GetMapping
    ResponseEntity<?> findAllPostsBySong(
            @RequestParam UUID songId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "latest") String sort);

    @PatchMapping("/{postId}")
    ResponseEntity<?> updatePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdatePostRequestDto request);

    @DeleteMapping("/{postId}")
    ResponseEntity<?> deletePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId);

    @PostMapping("/{postId}/instruments")
    ResponseEntity<?> addInstrumentConfig(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody AddInstrumentConfigRequestDto request);

    @GetMapping("/{postId}/instruments")
    ResponseEntity<?> findInstrumentConfigs(@PathVariable UUID postId);

    @DeleteMapping("/{postId}/instruments/{configId}")
    ResponseEntity<?> deleteInstrumentConfig(
            @PathVariable UUID postId,
            @PathVariable UUID configId,
            @RequestHeader("X-User-Id") UUID userId);
}
