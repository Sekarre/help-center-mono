package com.sekarre.chatdemo.controllers;

import com.sekarre.chatdemo.DTO.chat.ChatCreateRequestDTO;
import com.sekarre.chatdemo.DTO.chat.ChatInfoDTO;
import com.sekarre.chatdemo.DTO.chat.ChatMessageDTO;
import com.sekarre.chatdemo.security.perms.AdminPermission;
import com.sekarre.chatdemo.security.perms.ChatReadPermission;
import com.sekarre.chatdemo.services.chat.ChatService;
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

    @ChatReadPermission
    @GetMapping("/{channelId}")
    public ResponseEntity<List<ChatMessageDTO>> getAllChatMessages(@PathVariable String channelId) {
        return ResponseEntity.ok(chatService.getAllChatMessages(channelId));
    }

    @AdminPermission
    @PatchMapping("/{channelId}")
    public ResponseEntity<?> joinChat(@PathVariable String channelId) {
        chatService.joinChat(channelId);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ChatInfoDTO> createNewChatWithUsers(@RequestBody ChatCreateRequestDTO chatCreateRequestDTO) {
        return ResponseEntity.ok(chatService.createNewChatWithUsers(chatCreateRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ChatInfoDTO>> getChatChannelIds() {
        return ResponseEntity.ok(chatService.getChatChatInfo());
    }
}
