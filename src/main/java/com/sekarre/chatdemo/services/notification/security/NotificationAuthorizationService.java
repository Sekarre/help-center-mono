package com.sekarre.chatdemo.services.notification.security;

import com.sekarre.chatdemo.domain.enums.EventType;

public interface NotificationAuthorizationService {

    boolean checkIfUserAuthorizedToEventNotification(String destinationId, EventType eventType);
}
