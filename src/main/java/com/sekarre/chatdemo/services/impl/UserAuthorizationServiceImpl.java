package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.domain.Chat;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.exceptions.ChatAuthorizationException;
import com.sekarre.chatdemo.exceptions.ChatNotFoundException;
import com.sekarre.chatdemo.repositories.ChatRepository;
import com.sekarre.chatdemo.services.UserAuthorizationService;
import com.sekarre.chatdemo.security.UserDetailsHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserAuthorizationServiceImpl implements UserAuthorizationService {

    private final ChatRepository chatRepository;

    @Override
    public boolean checkIfUserIsAuthorizedToJoinChannel(String channelId) {
        if (!getChatByChannelIdWithUsers(channelId).getUsers().contains(UserDetailsHelper.getCurrentUser())) {
            throw new ChatAuthorizationException("User is not authorized to join channel with channel id: " + channelId);
        }
        return true;
    }

    @Override
    public boolean checkIfUserIsAuthorizedToJoinChannel(User user, String channelId) {
        if (!getChatByChannelIdWithUsers(channelId).getUsers().contains(user)) {
            throw new ChatAuthorizationException("User is not authorized to join channel with channel id: " + channelId);
        }
        return true;
    }

    private Chat getChatByChannelIdWithUsers(String channelId) {
        return chatRepository.findByChannelIdWithUsers(channelId)
                .orElseThrow(() -> new ChatNotFoundException("Chat with channel id: " + channelId + " not found"));
    }
}
