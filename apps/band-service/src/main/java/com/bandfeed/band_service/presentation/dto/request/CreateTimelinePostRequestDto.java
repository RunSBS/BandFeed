package com.bandfeed.band_service.presentation.dto.request;

import java.util.UUID;

public record CreateTimelinePostRequestDto(
        UUID bandId,
        String title,
        String content
) {}
