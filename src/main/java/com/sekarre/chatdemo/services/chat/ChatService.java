package com.sekarre.chatdemo.services.chat;

import com.sekarre.chatdemo.DTO.chat.ChatCreateRequestDTO;
import com.sekarre.chatdemo.DTO.chat.ChatInfoDTO;
import com.sekarre.chatdemo.DTO.chat.ChatMessageDTO;
import com.sekarre.chatdemo.domain.Chat;
import com.sekarre.chatdemo.domain.User;

import java.util.List;

public interface ChatService {

    Chat createNewChatWithUsers(String channelName, List<User> users);

    ChatInfoDTO createNewChatWithUsers(ChatCreateRequestDTO chatCreateRequestDTO);

    void joinChat(String channelId);

    ChatMessageDTO savePrivateChatMessage(ChatMessageDTO chatMessageDTO, String channelId);

    List<ChatMessageDTO> getAllChatMessages(String channelId);

    List<ChatInfoDTO> getChatChatInfo();
}
