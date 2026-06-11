package com.bandfeed.chat_service.presentation.controller;

import com.bandfeed.chat_service.application.ChatRoomService;
import com.bandfeed.chat_service.domain.model.ChatRoom;
import com.bandfeed.chat_service.domain.model.ChatRoomMember;
import com.bandfeed.chat_service.domain.repository.ChatRoomMemberRepository;
import com.bandfeed.chat_service.presentation.docs.ChatRoomControllerDocs;
import com.bandfeed.chat_service.presentation.dto.request.CreateChatRoomRequestDto;
import com.bandfeed.chat_service.presentation.dto.response.ChatRoomMemberResponseDto;
import com.bandfeed.chat_service.presentation.dto.response.ChatRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController implements ChatRoomControllerDocs {

    private final ChatRoomService chatRoomService;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    @PostMapping
    public ResponseEntity<?> createOrFindChatRoom(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateChatRoomRequestDto request) {
        ChatRoom room = chatRoomService.findOrCreateRoom(userId, request.targetUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ChatRoomResponseDto.from(room));
    }

    @Override
    @GetMapping
    public ResponseEntity<?> findMyChatRooms(@RequestHeader("X-User-Id") UUID userId) {
        List<ChatRoomResponseDto> rooms = chatRoomService.findMyRooms(userId).stream()
                .map(ChatRoomResponseDto::from)
                .toList();
        return ResponseEntity.ok(rooms);
    }

    @Override
    @GetMapping("/{roomId}")
    public ResponseEntity<?> findChatRoomById(
            @PathVariable UUID roomId,
            @RequestHeader("X-User-Id") UUID userId) {
        ChatRoom room = chatRoomService.findRoom(roomId);
        chatRoomService.validateParticipant(room, userId);
        return ResponseEntity.ok(ChatRoomResponseDto.from(room));
    }

    @Override
    @GetMapping("/{roomId}/members")
    public ResponseEntity<?> findChatRoomMembers(
            @PathVariable UUID roomId,
            @RequestHeader("X-User-Id") UUID userId) {
        ChatRoom room = chatRoomService.findRoom(roomId);
        chatRoomService.validateParticipant(room, userId);
        List<ChatRoomMemberResponseDto> members = chatRoomMemberRepository.findAllByChatRoomId(roomId).stream()
                .map(ChatRoomMemberResponseDto::from)
                .toList();
        return ResponseEntity.ok(members);
    }
}
