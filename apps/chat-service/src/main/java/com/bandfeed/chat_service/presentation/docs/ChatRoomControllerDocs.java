package com.bandfeed.chat_service.presentation.docs;

import com.bandfeed.chat_service.presentation.dto.request.CreateChatRoomRequestDto;
import com.bandfeed.chat_service.presentation.dto.request.ReadMessageRequestDto;
import com.bandfeed.chat_service.presentation.dto.response.ChatRoomMemberResponseDto;
import com.bandfeed.chat_service.presentation.dto.response.ChatRoomResponseDto;
import common.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface ChatRoomControllerDocs {

    @PostMapping
    ResponseEntity<CommonResponse<ChatRoomResponseDto>> createOrFindChatRoom(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateChatRoomRequestDto request);

    @GetMapping
    ResponseEntity<CommonResponse<List<ChatRoomResponseDto>>> findMyChatRooms(@RequestHeader("X-User-Id") UUID userId);

    @GetMapping("/{roomId}")
    ResponseEntity<CommonResponse<ChatRoomResponseDto>> findChatRoomById(
            @PathVariable UUID roomId,
            @RequestHeader("X-User-Id") UUID userId);

    @GetMapping("/{roomId}/members")
    ResponseEntity<CommonResponse<List<ChatRoomMemberResponseDto>>> findChatRoomMembers(
            @PathVariable UUID roomId,
            @RequestHeader("X-User-Id") UUID userId);

    @PatchMapping("/{roomId}/read-status")
    ResponseEntity<CommonResponse<?>> updateReadStatus(
            @PathVariable UUID roomId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody ReadMessageRequestDto request);
}
