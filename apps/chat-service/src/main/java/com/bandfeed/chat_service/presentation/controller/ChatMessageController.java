package com.bandfeed.chat_service.presentation.controller;

import com.bandfeed.chat_service.application.ChatMessageService;
import com.bandfeed.chat_service.domain.model.ChatMessage;
import com.bandfeed.chat_service.presentation.docs.ChatMessageControllerDocs;
import com.bandfeed.chat_service.presentation.dto.request.CreateChatMessageRequestDto;
import com.bandfeed.chat_service.presentation.dto.response.ChatMessageResponseDto;
import com.bandfeed.chat_service.presentation.dto.response.UnreadCountResponseDto;
import common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat-messages")
@RequiredArgsConstructor
public class ChatMessageController implements ChatMessageControllerDocs {

    private final ChatMessageService chatMessageService;

    @Override
    @GetMapping
    public ResponseEntity<CommonResponse<List<ChatMessageResponseDto>>> findMessages(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam UUID chatRoomId,
            @RequestParam(defaultValue = "50") int size) {
        List<ChatMessageResponseDto> messages = chatMessageService.findMessages(chatRoomId, userId, size).stream()
                .map(ChatMessageResponseDto::from)
                .toList();
        return CommonResponse.ok(messages);
    }

    @Override
    @PostMapping
    public ResponseEntity<CommonResponse<ChatMessageResponseDto>> sendMessage(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateChatMessageRequestDto request) {
        ChatMessage message = chatMessageService.sendMessage(request.chatRoomId(), userId, request.content());
        return CommonResponse.created("메시지가 전송되었습니다.", ChatMessageResponseDto.from(message));
    }

    @Override
    @GetMapping("/unread-count")
    public ResponseEntity<CommonResponse<UnreadCountResponseDto>> countUnread(@RequestHeader("X-User-Id") UUID userId) {
        long count = chatMessageService.countTotalUnread(userId);
        return CommonResponse.ok(new UnreadCountResponseDto(count));
    }
}
