package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.DTO.EventNotificationDTO;
import com.sekarre.chatdemo.domain.enums.SseEventType;

import java.util.List;

public interface EventNotificationService {

    void saveEventNotification(SseEventType sseEventType, String channelId, Long userId);

    List<EventNotificationDTO> getAllRemainingNotifications();

    void markNotificationAsRead(Long id);

    void stopNotificationForChannel(String channelId, Long userId);

    boolean isNotificationLimited(String channelId, Long userId);

    void startNotificationForChannel(String channelId, Long userId);
}
