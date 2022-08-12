package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.domain.enums.EventType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EventEmitterService {

    void removeEmitter(Long userId);

    SseEmitter createNewEmitter();

    /**
     * Sends new event message to current logged-in user
     * @param eventType
     * @param channelId
     */
    void sendNewEventMessage(EventType eventType, String channelId);

    /**
     * Sends new event message to all users with id in usersId parameter
     * @param eventType
     * @param channelId
     * @param usersId
     */
    void sendNewEventMessage(EventType eventType, String channelId, Long[] usersId);
}
