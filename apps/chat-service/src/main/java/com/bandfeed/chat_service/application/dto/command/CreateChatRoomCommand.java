package com.bandfeed.chat_service.application.dto.command;

import java.util.UUID;

public record CreateChatRoomCommand(
        UUID bandId,
        String roomName
) {}
