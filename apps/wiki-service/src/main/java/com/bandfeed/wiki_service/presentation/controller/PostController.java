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
import common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<CommonResponse<PostResponseDto>> createPost(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreatePostRequestDto request) {
        Post post = postService.createPost(new CreatePostCommand(
                request.songId(), userId, request.bandId(), request.bandName(), request.bandImageUrl(),
                request.title(), request.content()));
        UserInfo author = fetchAuthor(userId);
        return CommonResponse.created("게시글이 작성되었습니다.",
                PostResponseDto.from(post, author.getNickname(), author.getProfileImageUrl()));
    }

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponse<PostResponseDto>> findPostById(@PathVariable UUID postId) {
        Post post = postService.findPost(postId);
        UserInfo author = fetchAuthor(post.getAuthorId());
        return CommonResponse.ok(PostResponseDto.from(post, author.getNickname(), author.getProfileImageUrl()));
    }

    @Override
    @GetMapping
    public ResponseEntity<CommonResponse<Page<PostResponseDto>>> findAllPostsBySong(
            @RequestParam UUID songId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "latest") String sort) {
        Sort.Direction direction = "oldest".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "createdAt"));
        Page<Post> posts = postService.findPostsBySong(songId, pageable);

        List<UUID> authorIds = posts.stream().map(Post::getAuthorId).distinct().toList();
        Map<UUID, UserInfo> authorMap = fetchAuthors(authorIds);

        return CommonResponse.ok(posts.map(p -> {
            UserInfo author = authorMap.getOrDefault(p.getAuthorId(), new UserInfo(p.getAuthorId(), "알 수 없음", null));
            return PostResponseDto.from(p, author.getNickname(), author.getProfileImageUrl());
        }));
    }

    @Override
    @PatchMapping("/{postId}")
    public ResponseEntity<CommonResponse<PostResponseDto>> updatePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody UpdatePostRequestDto request) {
        Post post = postService.updatePost(postId, request.title(), request.content(), userId);
        UserInfo author = fetchAuthor(userId);
        return CommonResponse.ok(PostResponseDto.from(post, author.getNickname(), author.getProfileImageUrl()));
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponse<?>> deletePost(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId) {
        postService.deletePost(postId, userId);
        return CommonResponse.ok("게시글이 삭제되었습니다.");
    }

    @Override
    @PostMapping("/{postId}/instruments")
    public ResponseEntity<CommonResponse<InstrumentConfigResponseDto>> addInstrumentConfig(
            @PathVariable UUID postId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody AddInstrumentConfigRequestDto request) {
        return CommonResponse.created("악기 구성이 추가되었습니다.",
                InstrumentConfigResponseDto.from(postService.addInstrumentConfig(postId, request.instrumentType(), userId)));
    }

    @Override
    @GetMapping("/{postId}/instruments")
    public ResponseEntity<CommonResponse<List<InstrumentConfigResponseDto>>> findInstrumentConfigs(@PathVariable UUID postId) {
        List<InstrumentConfigResponseDto> configs = postService.findInstrumentConfigs(postId).stream()
                .map(InstrumentConfigResponseDto::from)
                .toList();
        return CommonResponse.ok(configs);
    }

    @Override
    @DeleteMapping("/{postId}/instruments/{configId}")
    public ResponseEntity<CommonResponse<?>> deleteInstrumentConfig(
            @PathVariable UUID postId,
            @PathVariable UUID configId,
            @RequestHeader("X-User-Id") UUID userId) {
        postService.deleteInstrumentConfig(configId);
        return CommonResponse.ok("악기 구성이 삭제되었습니다.");
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
