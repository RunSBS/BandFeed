package com.bandfeed.wiki_service.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostCreatedEvent(UUID postId, UUID songId, UUID authorId, LocalDateTime occurredAt) {
}
