package com.bandfeed.wiki_service.presentation.dto.request;

public record CreatePostRequestDto(
        Long songId,
        String title,
        String content
) {}
