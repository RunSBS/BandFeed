package com.bandfeed.chat_service.presentation.docs;

import com.bandfeed.chat_service.presentation.dto.request.CreateChatRoomRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface ChatRoomControllerDocs {

    @PostMapping
    ResponseEntity<?> createOrFindChatRoom(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateChatRoomRequestDto request);

    @GetMapping
    ResponseEntity<?> findMyChatRooms(@RequestHeader("X-User-Id") UUID userId);

    @GetMapping("/{roomId}")
    ResponseEntity<?> findChatRoomById(
            @PathVariable UUID roomId,
            @RequestHeader("X-User-Id") UUID userId);

    @GetMapping("/{roomId}/members")
    ResponseEntity<?> findChatRoomMembers(
            @PathVariable UUID roomId,
            @RequestHeader("X-User-Id") UUID userId);
}
