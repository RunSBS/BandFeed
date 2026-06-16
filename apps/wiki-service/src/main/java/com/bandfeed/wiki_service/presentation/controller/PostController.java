package com.bandfeed.wiki_service.presentation.controller;

import com.bandfeed.wiki_service.application.PostService;
import com.bandfeed.wiki_service.application.dto.command.CreatePostCommand;
import com.bandfeed.wiki_service.domain.model.Post;
import com.bandfeed.wiki_service.infrastructure.client.user.UserClient;
import com.bandfeed.wiki_service.infrastructure.client.user.UserClient.UserInfo;
import com.bandfeed.wiki_service.presentation.docs.PostControllerDocs;
import com.bandfeed.wiki_service.presentation.dto.request.AddInstrumentConfigRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.CreatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.request.UpdatePostRequestDto;
import com.bandfeed.wiki_service.presentation.dto.response.InstrumentConfigResponseDto;
import com.bandfeed.wiki_service.presentation.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wiki-posts")
@RequiredArgsConstructor
public class PostController implements PostControllerDocs {

    private final PostService postService;
    private final UserClient userClient;

    @Override
    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreatePostRequestDto request) {
        Post post = postService.createPost(new CreatePostCommand(
                request.songId(), userId, request.bandId(), request.bandName(), request.bandImageUrl(),
                request.title(), request.content()));
        // 작성자 닉네임/프로필 조회
        UserInfo author = fetchAuthor(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostResponseDto.from(post, author.getNickname(), author.getProfileImageUrl()));
    }

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<?> findPostById(@PathVariable UUID postId) {
        Post post = postService.findPost(postId);
        UserInfo author = fetchAuthor(post.getAuthorId());
        return ResponseEntity.ok(PostResponseDto.from(post, author.getNickname(), author.getProfileImageUrl()));
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
        Page<Post> posts = postService.findPostsBySong(songId, pageable);

        // 고유 authorId 배치 조회
        List<UUID> authorIds = posts.stream()
                .map(Post::getAuthorId)
                .distinct()
                .toList();
        Map<UUID, UserInfo> authorMap = fetchAuthors(authorIds);

        return ResponseEntity.ok(posts.map(p -> {
            UserInfo author = authorMap.getOrDefault(p.getAuthorId(),
                    new UserInfo(p.getAuthorId(), "알 수 없음", null));
            return PostResponseDto.from(p, author.getNickname(), author.getProfileImageUrl());
        }));
    }

    @Override
    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdatePostRequestDto request) {
        Post post = postService.updatePost(postId, request.title(), request.content(), userId);
        UserInfo author = fetchAuthor(userId);
        return ResponseEntity.ok(PostResponseDto.from(post, author.getNickname(), author.getProfileImageUrl()));
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

    private UserInfo fetchAuthor(UUID userId) {
        try {
            List<UserInfo> users = userClient.getUsersByIds(List.of(userId));
            return users.isEmpty() ? new UserInfo(userId, "알 수 없음", null) : users.get(0);
        } catch (Exception e) {
            return new UserInfo(userId, "알 수 없음", null);
        }
    }

    private Map<UUID, UserInfo> fetchAuthors(List<UUID> ids) {
        try {
            return userClient.getUsersByIds(ids).stream()
                    .collect(Collectors.toMap(UserInfo::getId, u -> u));
        } catch (Exception e) {
            return Map.of();
        }
    }
}
