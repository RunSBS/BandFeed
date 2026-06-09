package com.bandfeed.wiki_service.application.dto.command;

public record CreatePostCommand(
        Long songId,
        Long authorId,
        String title,
        String content
) {}
