package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;
import com.sekarre.chatdemo.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sekarre.chatdemo.controllers.ChatController.BASE_CHAT_URL;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = BASE_CHAT_URL)
public class ChatController {

    public static final String BASE_CHAT_URL = "/api/v1/chat-info";

    private final ChatService chatService;

    @GetMapping("/{chatId}")
    public List<ChatMessageDTO> getAllChatMessages(@PathVariable Long chatId) {
        return chatService.getAllChatMessages(chatId);
    }
}
