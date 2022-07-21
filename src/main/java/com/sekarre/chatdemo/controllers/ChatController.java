package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;
import com.sekarre.chatdemo.services.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ChatMessageDTO>> getAllChatMessages(@PathVariable Long chatId) {
        return ResponseEntity.ok(chatService.getAllChatMessages(chatId));
    }

    @PatchMapping("/{channelId}")
    public ResponseEntity<?> joinChat(@PathVariable String channelId) {
        chatService.joinChat(channelId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<String> createNewChat() {
        return ResponseEntity.ok(chatService.createNewChat());
    }

    @GetMapping
    public ResponseEntity<List<String>> getChatChannelIds() {
        return ResponseEntity.ok(chatService.getChatChannelIds());
    }
}
