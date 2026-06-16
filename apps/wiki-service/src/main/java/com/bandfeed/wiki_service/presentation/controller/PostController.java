package com.bandfeed.wiki_service.presentation.controller;

import com.bandfeed.wiki_service.application.PostService;
import com.bandfeed.wiki_service.application.dto.command.CreatePostCommand;
import com.bandfeed.wiki_service.domain.model.Post;
import com.bandfeed.wiki_service.presentation.docs.PostControllerDocs;
import com.bandfeed.wiki_service.presentation.dto.request.AddInstrumentConfigRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.CreatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.UpdatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.response.InstrumentConfigResponseDto;
import com.bandfeed.wiki_service.presentation.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wiki-posts")
@RequiredArgsConstructor
public class PostController implements PostControllerDocs {

    private final PostService postService;

    @Override
    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreatePostRequestDto request) {
        Post post = postService.createPost(new CreatePostCommand(request.songId(), userId, request.title(), request.content()));
        return ResponseEntity.status(HttpStatus.CREATED).body(PostResponseDto.from(post));
    }

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<?> findPostById(@PathVariable UUID postId) {
        return ResponseEntity.ok(PostResponseDto.from(postService.findPost(postId)));
    }

    @Override
    @GetMapping
    public ResponseEntity<?> findAllPostsBySong(
            @RequestParam UUID songId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "latest") String sort) {
        Sort.Direction direction = "oldest".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "createdAt"));
        return ResponseEntity.ok(postService.findPostsBySong(songId, pageable).map(PostResponseDto::from));
    }

    @Override
    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdatePostRequestDto request) {
        Post post = postService.updatePost(postId, request.title(), request.content(), userId);
        return ResponseEntity.ok(PostResponseDto.from(post));
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/{postId}/instruments")
    public ResponseEntity<?> addInstrumentConfig(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody AddInstrumentConfigRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(InstrumentConfigResponseDto.from(
                        postService.addInstrumentConfig(postId, request.instrumentType(), userId)));
    }

    @Override
    @GetMapping("/{postId}/instruments")
    public ResponseEntity<?> findInstrumentConfigs(@PathVariable UUID postId) {
        List<InstrumentConfigResponseDto> configs = postService.findInstrumentConfigs(postId).stream()
                .map(InstrumentConfigResponseDto::from)
                .toList();
        return ResponseEntity.ok(configs);
    }

    @Override
    @DeleteMapping("/{postId}/instruments/{configId}")
    public ResponseEntity<?> deleteInstrumentConfig(
            @PathVariable UUID postId,
            @PathVariable UUID configId,
            @RequestHeader("X-User-Id") UUID userId) {
        postService.deleteInstrumentConfig(configId);
        return ResponseEntity.noContent().build();
    }
}
