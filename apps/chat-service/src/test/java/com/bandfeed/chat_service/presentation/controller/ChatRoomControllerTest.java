package com.bandfeed.chat_service.presentation.controller;

import com.bandfeed.chat_service.application.ChatRoomService;
import com.bandfeed.chat_service.domain.model.ChatRoom;
import com.bandfeed.chat_service.presentation.dto.request.CreateChatRoomRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ChatRoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChatRoomService chatRoomService;

    @Test
    void createOrFindChatRoom_성공() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID targetUserId = UUID.randomUUID();

        CreateChatRoomRequestDto request = new CreateChatRoomRequestDto(targetUserId);

        mockMvc.perform(post("/api/chat-rooms")
                        .header("X-User-Id", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void findMyChatRooms_성공() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID targetUserId = UUID.randomUUID();
        chatRoomService.findOrCreateRoom(userId, targetUserId);

        mockMvc.perform(get("/api/chat-rooms")
                        .header("X-User-Id", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void findChatRoomById_참여자가_아니면_403() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID targetUserId = UUID.randomUUID();
        ChatRoom room = chatRoomService.findOrCreateRoom(userId, targetUserId);

        mockMvc.perform(get("/api/chat-rooms/{roomId}", room.getId())
                        .header("X-User-Id", UUID.randomUUID().toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void findChatRoomMembers_성공() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID targetUserId = UUID.randomUUID();
        ChatRoom room = chatRoomService.findOrCreateRoom(userId, targetUserId);

        mockMvc.perform(get("/api/chat-rooms/{roomId}/members", room.getId())
                        .header("X-User-Id", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
