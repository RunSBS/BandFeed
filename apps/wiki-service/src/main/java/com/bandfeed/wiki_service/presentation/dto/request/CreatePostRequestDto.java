package com.bandfeed.wiki_service.presentation.dto.request;

import java.util.UUID;

public record CreatePostRequestDto(
        UUID songId,
        UUID bandId,
        String bandName,
        String bandImageUrl,
        String title,
        String content
) {}
