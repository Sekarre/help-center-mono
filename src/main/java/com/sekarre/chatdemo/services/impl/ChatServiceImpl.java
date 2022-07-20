package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;
import com.sekarre.chatdemo.domain.Chat;
import com.sekarre.chatdemo.domain.ChatMessage;
import com.sekarre.chatdemo.mappers.ChatMessageMapper;
import com.sekarre.chatdemo.repositories.ChatMessageRepository;
import com.sekarre.chatdemo.repositories.ChatRepository;
import com.sekarre.chatdemo.services.ChatService;
import com.sekarre.chatdemo.util.UserDetailsHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRepository chatRepository;
    private final ChatMessageMapper chatMessageMapper;


    @Override
    public ChatMessageDTO savePrivateChatMessage(ChatMessageDTO chatMessageDTO, String channelId) {
        log.debug(chatMessageDTO.toString());

        ChatMessage chatMessage = createNewChatMessage(chatMessageDTO, channelId);

        return chatMessageMapper.mapMessageToChatMessageDTO(chatMessageRepository.save(chatMessage));
    }

    private ChatMessage createNewChatMessage(ChatMessageDTO chatMessageDTO, String channelId) {
        ChatMessage chatMessage = chatMessageMapper.mapChatMessageDTOToMessage(chatMessageDTO);
        Chat chat = getChatById(channelId);
        chatMessage.setChat(chat);
        chatMessage.setSender(UserDetailsHelper.getCurrentUser());
        return chatMessage;
    }

    private Chat getChatById(String channelId) {
        return chatRepository.findByChannelId(channelId)
                .orElseThrow(() -> new RuntimeException("Chat with channel id: " + channelId + " doesnt exist"));
    }

    @Override
    public List<ChatMessageDTO> getAllChatMessages(Long chatId) {
        return chatMessageRepository.findAllByChatIdOrderByCreatedDateTime(chatId).stream()
                .map(chatMessageMapper::mapMessageToChatMessageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getChatChannelIds() {
        return chatRepository.findAll().stream()
                .map(Chat::getChannelId)
                .collect(Collectors.toList());
    }
}
