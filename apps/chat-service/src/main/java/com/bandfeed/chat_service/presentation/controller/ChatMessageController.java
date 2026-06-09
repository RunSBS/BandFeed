package com.bandfeed.chat_service.presentation.controller;

import com.bandfeed.chat_service.presentation.docs.ChatMessageControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat-messages")
@RequiredArgsConstructor
public class ChatMessageController implements ChatMessageControllerDocs {
}
