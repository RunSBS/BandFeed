package com.bandfeed.chat_service.application.dto.command;

public record CreateChatRoomCommand(
        Long bandId,
        String roomName
) {}
