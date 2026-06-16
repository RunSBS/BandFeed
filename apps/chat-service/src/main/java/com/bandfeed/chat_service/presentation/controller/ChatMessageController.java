package com.bandfeed.chat_service.presentation.controller;

import com.bandfeed.chat_service.application.ChatMessageService;
import com.bandfeed.chat_service.domain.model.ChatMessage;
import com.bandfeed.chat_service.presentation.docs.ChatMessageControllerDocs;
import com.bandfeed.chat_service.presentation.dto.request.CreateChatMessageRequestDto;
import com.bandfeed.chat_service.presentation.dto.request.ReadMessageRequestDto;
import com.bandfeed.chat_service.presentation.dto.response.ChatMessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat-messages")
@RequiredArgsConstructor
public class ChatMessageController implements ChatMessageControllerDocs {

    private final ChatMessageService chatMessageService;

    @Override
    @GetMapping
    public ResponseEntity<?> findMessages(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam UUID chatRoomId,
            @RequestParam(defaultValue = "50") int size) {
        List<ChatMessageResponseDto> messages = chatMessageService.findMessages(chatRoomId, userId, size).stream()
                .map(ChatMessageResponseDto::from)
                .toList();
        return ResponseEntity.ok(messages);
    }

    @Override
    @PostMapping
    public ResponseEntity<?> sendMessage(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateChatMessageRequestDto request) {
        ChatMessage message = chatMessageService.sendMessage(request.chatRoomId(), userId, request.content());
        return ResponseEntity.status(HttpStatus.CREATED).body(ChatMessageResponseDto.from(message));
    }

    @Override
    @PatchMapping("/read")
    public ResponseEntity<?> readMessage(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody ReadMessageRequestDto request) {
        chatMessageService.readMessage(request.chatRoomId(), userId, request.lastReadMessageId());
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/unread-count")
    public ResponseEntity<?> countUnread(@RequestHeader("X-User-Id") UUID userId) {
        long count = chatMessageService.countTotalUnread(userId);
        return ResponseEntity.ok(Map.of("count", count));
    }
}
