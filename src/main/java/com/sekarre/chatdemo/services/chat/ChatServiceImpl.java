package com.sekarre.chatdemo.services.chat;

import com.sekarre.chatdemo.DTO.chat.ChatCreateRequestDTO;
import com.sekarre.chatdemo.DTO.chat.ChatInfoDTO;
import com.sekarre.chatdemo.DTO.chat.ChatMessageDTO;
import com.sekarre.chatdemo.domain.Chat;
import com.sekarre.chatdemo.domain.ChatMessage;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.domain.enums.EventType;
import com.sekarre.chatdemo.exceptions.chat.ChatNotFoundException;
import com.sekarre.chatdemo.mappers.ChatMapper;
import com.sekarre.chatdemo.mappers.ChatMessageMapper;
import com.sekarre.chatdemo.repositories.ChatMessageRepository;
import com.sekarre.chatdemo.repositories.ChatRepository;
import com.sekarre.chatdemo.services.notification.NotificationEmitterService;
import com.sekarre.chatdemo.services.user.UserService;
import com.sekarre.chatdemo.util.RandomStringGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sekarre.chatdemo.security.UserDetailsHelper.getCurrentUser;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRepository chatRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final ChatMapper chatMapper;
    private final NotificationEmitterService notificationEmitterService;
    private final UserService userService;

    @Override
    public Chat createNewChatWithUsers(String channelName, List<User> users) {
        return chatRepository.save(Chat.builder()
                .adminUser(getCurrentUser())
                .users(users)
                .channelId(getUniqueChannelId())
                .channelName(channelName)
                .build());
    }

    @Override
    public ChatInfoDTO createNewChatWithUsers(ChatCreateRequestDTO chatCreateRequestDTO) {
        return chatMapper.mapChatToChatInfoDTO(chatRepository.save(Chat.builder()
                .adminUser(getCurrentUser())
                .users(userService.getUsersByIds(chatCreateRequestDTO.getUsersId()))
                .channelId(getUniqueChannelId())
                .channelName(chatCreateRequestDTO.getChannelName())
                .build()));
    }

    @Override
    public void joinChat(String channelId) {
        Chat chat = getChatByChannelId(channelId);
        chat.addUser(getCurrentUser());
        chatRepository.save(chat);
    }

    @Override
    public ChatMessageDTO savePrivateChatMessage(ChatMessageDTO chatMessageDTO, String channelId) {
        log.debug("User id: " + getCurrentUser().getId() + " message: " + chatMessageDTO.getMessage());
        ChatMessage chatMessage = createNewChatMessage(chatMessageDTO, channelId);
        ChatMessageDTO returnChatMessageDTO = chatMessageMapper.mapMessageToChatMessageDTO(chatMessageRepository.save(chatMessage));
        sendEventMessage(channelId, chatMessage);
        return returnChatMessageDTO;
    }

    private void sendEventMessage(String channelId, ChatMessage chatMessage) {
        notificationEmitterService.sendNewEventMessage(
                EventType.NEW_CHAT_MESSAGE,
                channelId,
                chatMessage.getChat().getUsers().stream()
                        .map(User::getId)
                        .filter(id -> !id.equals(getCurrentUser().getId()))
                        .toArray(Long[]::new));
    }

    private ChatMessage createNewChatMessage(ChatMessageDTO chatMessageDTO, String channelId) {
        ChatMessage chatMessage = chatMessageMapper.mapChatMessageDTOToMessage(chatMessageDTO);
        Chat chat = getChatByChannelIdWithUsers(channelId);
        chatMessage.setChat(chat);
        chatMessage.setSender(getCurrentUser());
        return chatMessage;
    }

    @Override
    public List<ChatMessageDTO> getAllChatMessages(String channelId) {
        Chat chat = getChatByChannelId(channelId);
        return chatMessageRepository.findAllByChatIdOrderByCreatedDateTime(chat.getId()).stream()
                .map(chatMessageMapper::mapMessageToChatMessageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatInfoDTO> getChatChatInfo() {
        return chatRepository.findAll().stream()
                .map(chatMapper::mapChatToChatInfoDTO)
                .collect(Collectors.toList());
    }

    private String getUniqueChannelId() {
        String generatedChannelId = RandomStringGeneratorUtil.getRandomString();
        if (chatRepository.existsByChannelId(generatedChannelId)) {
            return getUniqueChannelId();
        }
        return generatedChannelId;
    }

    private Chat getChatByChannelId(String channelId) {
        return chatRepository.findByChannelId(channelId)
                .orElseThrow(() -> new ChatNotFoundException("Chat with channel id: " + channelId + " not found"));
    }

    private Chat getChatByChannelIdWithUsers(String channelId) {
        return chatRepository.findByChannelIdWithUsers(channelId)
                .orElseThrow(() -> new ChatNotFoundException("Chat with channel id: " + channelId + " not found"));
    }
}
