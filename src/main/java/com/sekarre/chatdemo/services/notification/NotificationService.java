package com.sekarre.chatdemo.services.notification;

import com.sekarre.chatdemo.DTO.notification.NotificationDTO;
import com.sekarre.chatdemo.domain.enums.EventType;

import java.util.List;

public interface NotificationService {

    void saveEventNotification(EventType eventType, String destinationId, Long userId);

    List<NotificationDTO> getAllUnreadNotifications();

    Integer getNotificationCount(String destinationId, EventType eventType);

    void markNotificationAsRead(String destinationId, EventType eventType);

    void markNotificationAsRead(String destinationId, EventType... eventType);

    void stopNotificationForDestination(String destinationId, Long userId, EventType eventType);

    /***
     * returns true if notifications to destination has been stopped, false otherwise
     * @param destinationId
     * @param userId
     * @return
     */
    boolean isNotificationStopped(String destinationId, Long userId, EventType eventType);

    void startNotificationForDestination(String destinationId, Long userId, EventType eventType);
}
