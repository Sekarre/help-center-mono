package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.ChatMessageDTO;
import com.sekarre.chatdemo.domain.Chat;
import com.sekarre.chatdemo.domain.ChatMessage;
import com.sekarre.chatdemo.mappers.ChatMessageMapper;
import com.sekarre.chatdemo.repositories.ChatMessageRepository;
import com.sekarre.chatdemo.repositories.ChatRepository;
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
    public ChatMessageDTO saveChatMessage(ChatMessageDTO chatMessageDTO) {
        log.debug(chatMessageDTO.toString());

        //Todo: wyciagnac uzytkownika z kontekstu springa

        return chatMessageMapper.mapMessageToChatMessageDTO(
                chatMessageRepository.save(chatMessageMapper.mapChatMessageDTOToMessage(chatMessageDTO)));
    }

    @Override
    public ChatMessageDTO savePrivateChatMessage(ChatMessageDTO chatMessageDTO, String channelId) {
        log.debug(chatMessageDTO.toString());

        //Todo: wyciagnac uzytkownika z kontekstu springa

        ChatMessage chatMessage = chatMessageMapper.mapChatMessageDTOToMessage(chatMessageDTO);
        Chat chat = getChatById(channelId);
        chatMessage.setChat(chat);

        return chatMessageMapper.mapMessageToChatMessageDTO(chatMessageRepository.save(chatMessage));
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
}
