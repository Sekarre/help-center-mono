package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;

import java.util.List;

public interface ChatService {

    String createNewChat();

    void joinChat(String channelId);

    ChatMessageDTO savePrivateChatMessage(ChatMessageDTO chatMessageDTO, String channelId);

    List<ChatMessageDTO> getAllChatMessages(Long chatId);

    List<String> getChatChannelIds();
}
