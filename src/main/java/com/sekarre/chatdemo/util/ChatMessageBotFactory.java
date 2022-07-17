package com.sekarre.chatdemo.util;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;

public class ChatMessageBotFactory {

    public static final String adminName = "God";

    public static ChatMessageDTO getWelcomeChatMessage(String user) {
        return ChatMessageDTO.builder()
                .message(user + " dołączył do czatu")
                .senderName(adminName)
                .build();
    }

    public static ChatMessageDTO getGoodbyeChatMessage(String user) {
        return ChatMessageDTO.builder()
                .message(user + " opuścił czat")
                .senderName(adminName)
                .build();
    }
}
