package com.bandfeed.chat_service.presentation.dto.request;

public record CreateChatRoomRequestDto(
        Long bandId,
        String roomName
) {}
