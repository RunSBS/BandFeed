package com.bandfeed.wiki_service.domain.event;

import java.time.LocalDateTime;

public record PostCreatedEvent(Long postId, Long songId, Long authorId, LocalDateTime occurredAt) {
}
