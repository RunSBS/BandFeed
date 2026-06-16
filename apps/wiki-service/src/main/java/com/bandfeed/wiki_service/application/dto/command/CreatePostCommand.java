package com.bandfeed.wiki_service.application.dto.command;

import java.util.UUID;

public record CreatePostCommand(
        UUID songId,
        UUID authorId,
        UUID bandId,
        String bandName,
        String bandImageUrl,
        String title,
        String content
) {}
