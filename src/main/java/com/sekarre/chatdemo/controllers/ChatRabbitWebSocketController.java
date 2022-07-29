package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;
import com.sekarre.chatdemo.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatRabbitWebSocketController {

    private final ChatService chatService;

    @MessageMapping("private-chat-room.{channelId}")
    @SendTo("/topic/private.{channelId}")
    public ChatMessageDTO getMessagePrivateChat(@Payload @Valid ChatMessageDTO chatMessageDTO,
                                                        @DestinationVariable String channelId) {
        return chatService.savePrivateChatMessage(chatMessageDTO, channelId);
    }
}
