package com.bandfeed.user_service.domain.event;

import java.time.LocalDateTime;

public record UserCreatedEvent(Long userId, String email, String nickname, LocalDateTime occurredAt) {}
