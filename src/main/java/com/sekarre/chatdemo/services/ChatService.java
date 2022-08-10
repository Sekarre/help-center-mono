package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.ChatInfoDTO;
import com.sekarre.chatdemo.DTO.ChatMessageDTO;
import com.sekarre.chatdemo.domain.Chat;

import java.util.List;

public interface ChatService {

    ChatInfoDTO createNewChat();

    Chat createNewChat(String channelName);

    void joinChat(String channelId);

    ChatMessageDTO savePrivateChatMessage(ChatMessageDTO chatMessageDTO, String channelId);

    List<ChatMessageDTO> getAllChatMessages(String channelId);

    List<ChatInfoDTO> getChatChatInfo();
}
