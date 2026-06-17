package com.bandfeed.wiki_service.presentation.dto.request;

public record SearchSongRequestDto(
        String keyword,
        int limit,
        int offset
) {}
