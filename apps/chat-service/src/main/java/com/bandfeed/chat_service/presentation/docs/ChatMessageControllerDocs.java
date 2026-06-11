package com.bandfeed.chat_service.presentation.docs;

import com.bandfeed.chat_service.presentation.dto.request.CreateChatMessageRequestDto;
import com.bandfeed.chat_service.presentation.dto.request.ReadMessageRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface ChatMessageControllerDocs {

    @GetMapping
    ResponseEntity<?> findMessages(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam UUID chatRoomId,
            @RequestParam(defaultValue = "50") int size);

    @PostMapping
    ResponseEntity<?> sendMessage(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateChatMessageRequestDto request);

    @PatchMapping("/read")
    ResponseEntity<?> readMessage(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody ReadMessageRequestDto request);
}
