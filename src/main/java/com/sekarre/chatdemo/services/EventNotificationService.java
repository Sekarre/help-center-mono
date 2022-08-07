package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.domain.enums.SseEventType;

public interface EventNotificationService {

    void saveEventNotification(SseEventType sseEventType, String channelId);
}
