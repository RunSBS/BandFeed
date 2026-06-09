package com.bandfeed.wiki_service.presentation.dto.request;

public record UpdatePostRequestDto(
        String title,
        String content
) {}
