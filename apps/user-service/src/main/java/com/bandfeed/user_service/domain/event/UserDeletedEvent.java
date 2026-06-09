package com.bandfeed.user_service.domain.event;

import java.time.LocalDateTime;

public record UserDeletedEvent(Long userId, LocalDateTime occurredAt) {}
