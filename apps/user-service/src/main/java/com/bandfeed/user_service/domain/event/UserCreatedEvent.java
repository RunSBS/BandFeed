package com.bandfeed.user_service.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreatedEvent(UUID userId, String email, String nickname, LocalDateTime occurredAt) {}
