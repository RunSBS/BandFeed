package com.bandfeed.chat_service.presentation.controller;

import com.bandfeed.chat_service.presentation.docs.ChatRoomControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController implements ChatRoomControllerDocs {
}
