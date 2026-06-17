package com.bandfeed.chat_service.presentation.docs;

import com.bandfeed.chat_service.presentation.dto.request.CreateChatMessageRequestDto;
import com.bandfeed.chat_service.presentation.dto.response.ChatMessageResponseDto;
import com.bandfeed.chat_service.presentation.dto.response.UnreadCountResponseDto;
import common.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface ChatMessageControllerDocs {

    @GetMapping
    ResponseEntity<CommonResponse<List<ChatMessageResponseDto>>> findMessages(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam UUID chatRoomId,
            @RequestParam(defaultValue = "50") int size);

    @PostMapping
    ResponseEntity<CommonResponse<ChatMessageResponseDto>> sendMessage(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateChatMessageRequestDto request);

    @GetMapping("/unread-count")
    ResponseEntity<CommonResponse<UnreadCountResponseDto>> countUnread(@RequestHeader("X-User-Id") UUID userId);
}
