package com.sekarre.chatdemo.listeners;

import com.sekarre.chatdemo.config.ProfilesHolder;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.exceptions.WebSocketAuthenticationException;
import com.sekarre.chatdemo.factories.ChatMessageBotFactory;
import com.sekarre.chatdemo.services.UserAuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.Profiles;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
@Profile(ProfilesHolder.DEFAULT)
public class ChatListener {

    private final Map<String, String> destinationTracker = new HashMap<>();
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserAuthorizationService userAuthorizationService;

    private static final String channelSeparatorRegex = "\\.";

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        final String destination = destinationTracker.get(headers.getSessionId());
        User user = getUserFromHeaders(headers);
        if (Objects.isNull(destination)) {
            return;
        }
        simpMessagingTemplate.convertAndSend(destination,
                ChatMessageBotFactory.getGoodbyeChatMessage(user.getName() + " " + user.getLastname()));
    }

    @EventListener
    public void onConnectEvent(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        User user = getUserFromHeaders(headers);
        userAuthorizationService.checkIfUserIsAuthorizedToJoinChannel(user, getChannelIdFromDestination(headers.getDestination()));
        destinationTracker.put(headers.getSessionId(), headers.getDestination());
        simpMessagingTemplate.convertAndSend(
                Objects.requireNonNull(headers.getDestination()),
                ChatMessageBotFactory.getWelcomeChatMessage(user.getName() + " " + user.getLastname()));
    }

    private String getChannelIdFromDestination(String destination) {
        String[] splitDest = destination.split(channelSeparatorRegex);
        return splitDest[splitDest.length - 1];
    }

    private User getUserFromHeaders(SimpMessageHeaderAccessor headers) {
        Principal principal =  headers.getUser();
        User user = (User) (principal != null ? ((UsernamePasswordAuthenticationToken) principal).getPrincipal() : null);

        if (Objects.isNull(user)) {
            throw new WebSocketAuthenticationException("User is not authenticated");
        }

        return user;
    }
}
