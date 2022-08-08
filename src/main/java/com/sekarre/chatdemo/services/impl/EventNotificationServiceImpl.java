package com.sekarre.chatdemo.services.impl;

import com.sekarre.chatdemo.DTO.EventNotificationDTO;
import com.sekarre.chatdemo.domain.EventNotification;
import com.sekarre.chatdemo.domain.EventNotificationLimiter;
import com.sekarre.chatdemo.domain.enums.SseEventType;
import com.sekarre.chatdemo.exceptions.notification.EventNotificationAuthorizationException;
import com.sekarre.chatdemo.exceptions.notification.EventNotificationNotFoundException;
import com.sekarre.chatdemo.factories.EventNotificationMessageFactory;
import com.sekarre.chatdemo.mappers.EventNotificationMapper;
import com.sekarre.chatdemo.repositories.EventNotificationRepository;
import com.sekarre.chatdemo.repositories.EventNotificationLimiterRepository;
import com.sekarre.chatdemo.services.EventNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sekarre.chatdemo.security.UserDetailsHelper.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventNotificationServiceImpl implements EventNotificationService {

    private final EventNotificationRepository eventNotificationRepository;
    private final EventNotificationLimiterRepository eventNotificationLimiterRepository;
    private final EventNotificationMapper eventNotificationMapper;

    @Override
    public void saveEventNotification(SseEventType sseEventType, String channelId, Long userId) {
        eventNotificationRepository.save(EventNotification.builder()
                .message(new EventNotificationMessageFactory().getEventNotificationMessage(sseEventType))
                .channelId(channelId)
                .userId(userId)
                .sseEventType(sseEventType)
                .build());
    }

    @Override
    public List<EventNotificationDTO> getAllRemainingNotifications() {
        return eventNotificationRepository.findAllByUserIdAndReadIsFalse(getCurrentUser().getId()).stream()
                .map(eventNotificationMapper::mapEventNotificationToEventNotificationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void markNotificationAsRead(String channelId) {
        List<EventNotification> eventNotifications = eventNotificationRepository.findAllByChannelIdAndUserId(channelId, getCurrentUser().getId());
        if (eventNotifications.isEmpty()) {
            return;
        }
        checkIfAuthorizedToEditNotification(eventNotifications.get(0));
        eventNotifications.forEach(eventNotification -> {
            eventNotification.setRead(true);
            eventNotificationRepository.save(eventNotification);
        });
    }

    @Override
    public void stopNotificationForChannel(String channelId, Long userId) {
        eventNotificationLimiterRepository.save(EventNotificationLimiter.builder().channelId(channelId).userId(userId).build());
    }


    @Override
    public boolean isNotificationStopped(String channelId, Long userId) {
        return eventNotificationLimiterRepository.existsByChannelIdAndUserId(channelId, userId);
    }

    @Override
    public void startNotificationForChannel(String channelId, Long userId) {
        eventNotificationLimiterRepository.deleteByChannelIdAndUserId(channelId, userId);
    }

    private void checkIfAuthorizedToEditNotification(EventNotification eventNotification) {
        Long userId = getCurrentUser().getId();
        if ( !eventNotification.getUserId().equals(userId)){
            throw new EventNotificationAuthorizationException(
                    "User with id: + " + userId + "is not authorized to edit notification with id: " + eventNotification.getId());
        }
    }

    private EventNotification getEventNotificationById(Long notificationEventId) {
        return eventNotificationRepository.findById(notificationEventId)
                .orElseThrow(() -> new EventNotificationNotFoundException(
                        "Event notification with notificationEventId: " + notificationEventId + " not found"));
    }
}
