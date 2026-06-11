package com.bandfeed.chat_service.presentation.controller;

import com.bandfeed.chat_service.application.ChatRoomService;
import com.bandfeed.chat_service.domain.model.ChatRoom;
import com.bandfeed.chat_service.presentation.dto.request.CreateChatMessageRequestDto;
import com.bandfeed.chat_service.presentation.dto.request.ReadMessageRequestDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ChatMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChatRoomService chatRoomService;

    @Test
    void sendMessage_및_조회_성공() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID targetUserId = UUID.randomUUID();
        ChatRoom room = chatRoomService.findOrCreateRoom(userId, targetUserId);

        CreateChatMessageRequestDto request = new CreateChatMessageRequestDto(room.getId(), "안녕하세요");

        mockMvc.perform(post("/api/chat-messages")
                        .header("X-User-Id", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("안녕하세요"));

        mockMvc.perform(get("/api/chat-messages")
                        .header("X-User-Id", targetUserId.toString())
                        .param("chatRoomId", room.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void sendMessage_참여자가_아니면_403() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID targetUserId = UUID.randomUUID();
        ChatRoom room = chatRoomService.findOrCreateRoom(userId, targetUserId);

        CreateChatMessageRequestDto request = new CreateChatMessageRequestDto(room.getId(), "안녕하세요");

        mockMvc.perform(post("/api/chat-messages")
                        .header("X-User-Id", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void readMessage_성공() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID targetUserId = UUID.randomUUID();
        ChatRoom room = chatRoomService.findOrCreateRoom(userId, targetUserId);

        CreateChatMessageRequestDto sendRequest = new CreateChatMessageRequestDto(room.getId(), "메시지");
        String response = mockMvc.perform(post("/api/chat-messages")
                        .header("X-User-Id", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sendRequest)))
                .andReturn().getResponse().getContentAsString();

        UUID messageId = UUID.fromString(objectMapper.readTree(response).get("id").asText());

        ReadMessageRequestDto readRequest = new ReadMessageRequestDto(room.getId(), messageId);
        mockMvc.perform(patch("/api/chat-messages/read")
                        .header("X-User-Id", targetUserId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(readRequest)))
                .andExpect(status().isOk());
    }
}
