package com.bandfeed.chat_service.presentation.dto.request;

import java.util.UUID;

public record ReadMessageRequestDto(
        UUID lastReadMessageId
) {}
