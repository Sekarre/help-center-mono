package com.sekarre.chatdemo.listeners;

import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.exceptions.WebSocketAuthenticationException;
import com.sekarre.chatdemo.util.ChatMessageBotFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ChatListener {

    private final Map<String, String> destinationTracker = new HashMap<>();
    private final SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        final String destination = destinationTracker.get(headers.getSessionId());
        log.debug(destination);
        User user = getUserFromHeaders(headers);
        simpMessagingTemplate.convertAndSend(
                Objects.requireNonNull(destination),
                ChatMessageBotFactory.getGoodbyeChatMessage(user.getName() + " " + user.getLastname()));
    }

    @EventListener
    public void onConnectEvent(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        destinationTracker.put(headers.getSessionId(), headers.getDestination());
        User user = getUserFromHeaders(headers);
        simpMessagingTemplate.convertAndSend(
                Objects.requireNonNull(headers.getDestination()),
                ChatMessageBotFactory.getWelcomeChatMessage(user.getName() + " " + user.getLastname()));
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
