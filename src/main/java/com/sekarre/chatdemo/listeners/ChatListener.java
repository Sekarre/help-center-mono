package com.sekarre.chatdemo.listeners;

import com.sekarre.chatdemo.config.ProfilesHolder;
import com.sekarre.chatdemo.domain.User;
import com.sekarre.chatdemo.domain.enums.EventType;
import com.sekarre.chatdemo.exceptions.handler.ListenerErrorHandler;
import com.sekarre.chatdemo.factories.ChatMessageBotFactory;
import com.sekarre.chatdemo.services.EventNotificationService;
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

import static com.sekarre.chatdemo.util.SimpMessageHeaderUtil.getChannelIdFromDestinationHeader;
import static com.sekarre.chatdemo.util.SimpMessageHeaderUtil.getUserFromHeaders;

@RequiredArgsConstructor
@Slf4j
@Component
@Profile(ProfilesHolder.NO_AUTH_DISABLED)
public class ChatListener {

    private final Map<String, String> destinationTracker = new HashMap<>();
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final EventNotificationService eventNotificationService;

    @ListenerErrorHandler
    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        final String destination = destinationTracker.get(headers.getSessionId());
        User user = getUserFromHeaders(headers);
        if (Objects.isNull(destination)) {
            return;
        }
        destinationTracker.remove(headers.getSessionId());
        eventNotificationService.startNotificationForDestination(
                getChannelIdFromDestinationHeader(destination), user.getId(), EventType.CHAT);
        simpMessagingTemplate.convertAndSend(destination,
                ChatMessageBotFactory.getGoodbyeChatMessage(user.getName() + " " + user.getLastname()));
    }

    @ListenerErrorHandler
    @EventListener
    public void onSubscribeEvent(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        User user = getUserFromHeaders(headers);
        final String destination = headers.getDestination();
        if (Objects.isNull(destination)) {
            return;
        }
        destinationTracker.put(headers.getSessionId(), destination);
        eventNotificationService.stopNotificationForDestination(
                getChannelIdFromDestinationHeader(destination), user.getId(), EventType.CHAT);
        simpMessagingTemplate.convertAndSend(destination,
                ChatMessageBotFactory.getWelcomeChatMessage(user.getName() + " " + user.getLastname()));
    }
}
