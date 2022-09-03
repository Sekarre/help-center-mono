package com.sekarre.chatdemo.services.notification.security;

import com.sekarre.chatdemo.domain.Notification;
import com.sekarre.chatdemo.domain.enums.EventType;
import com.sekarre.chatdemo.exceptions.notification.EventNotificationAuthorizationException;
import com.sekarre.chatdemo.exceptions.notification.EventNotificationNotFoundException;
import com.sekarre.chatdemo.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sekarre.chatdemo.security.UserDetailsHelper.getCurrentUser;

@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationAuthorizationServiceImpl implements NotificationAuthorizationService {

    private final NotificationRepository notificationRepository;

    @Override
    public boolean checkIfUserAuthorizedToEventNotification(String destinationId, EventType eventType) {
        if (!getEventNotificationByDestAndEventType(destinationId, eventType).getUserId().equals(getCurrentUser().getId())) {
            throw new EventNotificationAuthorizationException("User is not authorized to event notification");
        }
        return true;
    }

    private Notification getEventNotificationByDestAndEventType(String destinationId, EventType eventType) {
        return notificationRepository.findFirstByDestinationIdAndUserIdAndEventType(destinationId, getCurrentUser().getId(), eventType)
                .orElseThrow(() -> new EventNotificationNotFoundException("Event notification with destId: " + destinationId + " and " +
                        "event type: " + eventType + " not found"));
    }
}
