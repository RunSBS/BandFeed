package com.bandfeed.chat_service.presentation.controller;

import com.bandfeed.chat_service.application.ChatMessageService;
import com.bandfeed.chat_service.application.ChatRoomCreationResult;
import com.bandfeed.chat_service.application.ChatRoomService;
import com.bandfeed.chat_service.domain.model.ChatRoom;
import com.bandfeed.chat_service.domain.repository.ChatRoomMemberRepository;
import com.bandfeed.chat_service.presentation.docs.ChatRoomControllerDocs;
import com.bandfeed.chat_service.presentation.dto.request.CreateChatRoomRequestDto;
import com.bandfeed.chat_service.presentation.dto.request.ReadMessageRequestDto;
import com.bandfeed.chat_service.presentation.dto.response.ChatRoomMemberResponseDto;
import com.bandfeed.chat_service.presentation.dto.response.ChatRoomResponseDto;
import common.dto.CommonResponse;
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
    private final ChatMessageService chatMessageService;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    @PostMapping
    public ResponseEntity<CommonResponse<ChatRoomResponseDto>> createOrFindChatRoom(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody CreateChatRoomRequestDto request) {
        ChatRoomCreationResult result = chatRoomService.findOrCreateRoom(userId, request.targetUserId());
        HttpStatus status = result.created() ? HttpStatus.CREATED : HttpStatus.OK;
        String message = result.created() ? "채팅방이 생성되었습니다." : "기존 채팅방을 반환합니다.";
        return CommonResponse.of(status, message, ChatRoomResponseDto.from(result.room()));
    }

    @Override
    @GetMapping
    public ResponseEntity<CommonResponse<List<ChatRoomResponseDto>>> findMyChatRooms(@RequestHeader("X-User-Id") UUID userId) {
        List<ChatRoomResponseDto> rooms = chatRoomService.findMyRooms(userId).stream()
                .map(ChatRoomResponseDto::from)
                .toList();
        return CommonResponse.ok(rooms);
    }

    @Override
    @GetMapping("/{roomId}")
    public ResponseEntity<CommonResponse<ChatRoomResponseDto>> findChatRoomById(
            @PathVariable UUID roomId,
            @RequestHeader("X-User-Id") UUID userId) {
        ChatRoom room = chatRoomService.findRoom(roomId);
        chatRoomService.validateParticipant(room, userId);
        return CommonResponse.ok(ChatRoomResponseDto.from(room));
    }

    @Override
    @GetMapping("/{roomId}/members")
    public ResponseEntity<CommonResponse<List<ChatRoomMemberResponseDto>>> findChatRoomMembers(
            @PathVariable UUID roomId,
            @RequestHeader("X-User-Id") UUID userId) {
        ChatRoom room = chatRoomService.findRoom(roomId);
        chatRoomService.validateParticipant(room, userId);
        List<ChatRoomMemberResponseDto> members = chatRoomMemberRepository.findAllByChatRoomId(roomId).stream()
                .map(ChatRoomMemberResponseDto::from)
                .toList();
        return CommonResponse.ok(members);
    }

    @Override
    @PatchMapping("/{roomId}/read-status")
    public ResponseEntity<CommonResponse<?>> updateReadStatus(
            @PathVariable UUID roomId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody ReadMessageRequestDto request) {
        chatMessageService.readMessage(roomId, userId, request.lastReadMessageId());
        return CommonResponse.ok("읽음 처리가 완료되었습니다.");
    }
}
