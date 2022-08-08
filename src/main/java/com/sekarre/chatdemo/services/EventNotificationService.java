package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.EventNotificationDTO;
import com.sekarre.chatdemo.domain.enums.SseEventType;

import java.util.List;

public interface EventNotificationService {

    void saveEventNotification(SseEventType sseEventType, String channelId, Long userId);

    List<EventNotificationDTO> getAllRemainingNotifications();

    void markNotificationAsRead(String channelId);

    void stopNotificationForChannel(String channelId, Long userId);

    /***
     * returns true if notifications to channel has been stopped, false otherwise
     * @param channelId
     * @param userId
     * @return
     */
    boolean isNotificationStopped(String channelId, Long userId);

    void startNotificationForChannel(String channelId, Long userId);
}
