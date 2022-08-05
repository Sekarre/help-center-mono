package com.sekarre.chatdemo.listeners;

import com.sekarre.chatdemo.config.ProfilesHolder;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.exceptions.handler.ListenerErrorHandler;
import com.sekarre.chatdemo.factories.ChatMessageBotFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.sekarre.chatdemo.util.SimpMessageHeaderUtil.getUserFromHeaders;

@RequiredArgsConstructor
@Slf4j
@Component
@Profile(ProfilesHolder.NO_AUTH_DISABLED)
public class ChatListener {

    private final Map<String, String> destinationTracker = new HashMap<>();
    private final SimpMessagingTemplate simpMessagingTemplate;

    @ListenerErrorHandler
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

    @ListenerErrorHandler
    @EventListener
    public void onSubscribeEvent(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        User user = getUserFromHeaders(headers);
        destinationTracker.put(headers.getSessionId(), headers.getDestination());
        simpMessagingTemplate.convertAndSend(
                Objects.requireNonNull(headers.getDestination()),
                ChatMessageBotFactory.getWelcomeChatMessage(user.getName() + " " + user.getLastname()));
    }
}
