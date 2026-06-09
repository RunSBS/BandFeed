package com.bandfeed.wiki_service.presentation.dto.request;

import java.util.UUID;

public record CreatePostRequestDto(
        UUID songId,
        String title,
        String content
) {}
