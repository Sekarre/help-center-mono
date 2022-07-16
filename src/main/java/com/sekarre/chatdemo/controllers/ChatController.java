package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;
import com.sekarre.chatdemo.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

//    @MessageMapping("/chat.test")
//    @SendTo("/topic/public/{roomId}")
//    public ChatMessage getMessage(@DestinationVariable String roomId, ChatMessage chatMessage) {
//        return chatMessage;
//    }

    @MessageMapping("/chat-room")
    @SendTo("/room/private")
    public ChatMessageDTO getMessage(@Payload ChatMessageDTO chatMessageDTO) {
        log.debug(chatMessageDTO.getMessage());
        return chatMessageDTO;
    }
}
