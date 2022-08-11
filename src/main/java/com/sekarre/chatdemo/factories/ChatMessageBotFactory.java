package com.sekarre.chatdemo.factories;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;

public class ChatMessageBotFactory {

    public static final String botName = "ChatInfo";
    public static final Long botId = -1L;

    public static ChatMessageDTO getWelcomeChatMessage(String user) {
        return ChatMessageDTO.builder()
                .message(user + " joined chat")
                .senderName(botName)
                .senderId(botId)
                .build();
    }

    public static ChatMessageDTO getGoodbyeChatMessage(String user) {
        return ChatMessageDTO.builder()
                .message(user + " left chat")
                .senderName(botName)
                .senderId(botId)
                .build();
    }
}
