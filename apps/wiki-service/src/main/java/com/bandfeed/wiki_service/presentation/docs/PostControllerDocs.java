package com.bandfeed.wiki_service.presentation.docs;

import com.bandfeed.wiki_service.presentation.dto.request.AddInstrumentConfigRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.CreatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.UpdatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.response.InstrumentConfigResponseDto;
import com.bandfeed.wiki_service.presentation.dto.response.PostResponseDto;
import common.dto.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface PostControllerDocs {

    @PostMapping
    ResponseEntity<CommonResponse<PostResponseDto>> createPost(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreatePostRequestDto request);

    @GetMapping("/{postId}")
    ResponseEntity<CommonResponse<PostResponseDto>> findPostById(@PathVariable UUID postId);

    @GetMapping
    ResponseEntity<CommonResponse<Page<PostResponseDto>>> findAllPostsBySong(
            @RequestParam UUID songId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "latest") String sort);

    @PatchMapping("/{postId}")
    ResponseEntity<CommonResponse<PostResponseDto>> updatePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdatePostRequestDto request);

    @DeleteMapping("/{postId}")
    ResponseEntity<CommonResponse<?>> deletePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId);

    @PostMapping("/{postId}/instruments")
    ResponseEntity<CommonResponse<InstrumentConfigResponseDto>> addInstrumentConfig(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody AddInstrumentConfigRequestDto request);

    @GetMapping("/{postId}/instruments")
    ResponseEntity<CommonResponse<List<InstrumentConfigResponseDto>>> findInstrumentConfigs(@PathVariable UUID postId);

    @DeleteMapping("/{postId}/instruments/{configId}")
    ResponseEntity<CommonResponse<?>> deleteInstrumentConfig(
            @PathVariable UUID postId,
            @PathVariable UUID configId,
            @RequestHeader("X-User-Id") UUID userId);
}
