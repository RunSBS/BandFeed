package com.bandfeed.chat_service.infrastructure.websocket;

import com.bandfeed.chat_service.application.ChatMessageService;
import com.bandfeed.chat_service.domain.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompMessageHandler {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{roomId}/send")
    public void sendMessage(
            @DestinationVariable Long roomId,
            @Header("senderId") Long senderId,
            String content
    ) {
        ChatMessage message = chatMessageService.sendMessage(roomId, senderId, content);
        messagingTemplate.convertAndSend("/topic/chat/" + roomId, message);
    }
}
