package com.sekarre.chatdemo.services.security;

import com.sekarre.chatdemo.domain.enums.EventType;

public interface EventNotificationAuthorizationService {

    boolean checkIfUserAuthorizedToEventNotification(String destinationId, EventType eventType);
}
