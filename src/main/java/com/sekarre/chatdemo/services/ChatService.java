package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;

import java.util.List;

public interface ChatService {
    ChatMessageDTO saveChatMessage(ChatMessageDTO chatMessageDTO);

    ChatMessageDTO savePrivateChatMessage(ChatMessageDTO chatMessageDTO, String channelId);

    List<ChatMessageDTO> getAllChatMessages(Long chatId);
}