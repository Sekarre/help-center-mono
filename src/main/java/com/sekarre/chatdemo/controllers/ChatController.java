package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;
import com.sekarre.chatdemo.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/chat-info")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/{chatId}")
    public List<ChatMessageDTO> getAllChatMessages(@PathVariable Long chatId) {
        return chatService.getAllChatMessages(chatId);
    }
}
