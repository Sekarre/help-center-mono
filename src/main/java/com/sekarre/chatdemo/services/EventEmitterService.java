package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.domain.enums.EventType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EventEmitterService {

    void removeEmitter(Long userId);

    SseEmitter createNewEmitter();

    /**
     * Sends new event message to current logged-in user
     * @param eventType
     * @param destinationId
     */
    void sendNewEventMessage(EventType eventType, String destinationId);

    /**
     * Sends new event message to all users with id in usersId parameter
     * @param eventType
     * @param destinationId
     * @param usersId
     */
    void sendNewEventMessage(EventType eventType, String destinationId, Long[] usersId);
}
