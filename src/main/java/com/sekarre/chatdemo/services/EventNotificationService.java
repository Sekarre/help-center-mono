package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.EventNotificationDTO;
import com.sekarre.chatdemo.domain.enums.EventType;

import java.util.List;

public interface EventNotificationService {

    void saveEventNotification(EventType eventType, String destinationId, Long userId);

    List<EventNotificationDTO> getAllUnreadNotifications();

    void markNotificationAsRead(String destinationId, String eventType);

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
