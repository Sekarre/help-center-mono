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

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatWebSocketController {

    private final ChatService chatService;

    @MessageMapping("/chat-room")
    @SendTo("/room/public")
    public ChatMessageDTO getMessagePublicChat(@Payload @Valid ChatMessageDTO chatMessageDTO) {
        return chatService.saveChatMessage(chatMessageDTO);
    }

    @MessageMapping("/private-chat-room/{channelId}")
    @SendTo("/room/private/{channelId}")
    public ChatMessageDTO getMessagePrivateChat(@Payload @Valid ChatMessageDTO chatMessageDTO,
                                                @DestinationVariable String channelId) {
        return chatService.savePrivateChatMessage(chatMessageDTO, channelId);
    }
}
