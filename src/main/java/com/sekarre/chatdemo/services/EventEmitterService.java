package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.domain.enums.SseEventType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EventEmitterService {

    void removeEmitter(Long userId);

    SseEmitter createNewEmitter();

    /**
     * Sends new event message to current logged-in user
     * @param sseEventType
     * @param channelId
     */
    void sendNewEventMessage(SseEventType sseEventType, String channelId);

    /**
     * Sends new event message to all users with id in usersId parameter
     * @param sseEventType
     * @param channelId
     * @param usersId
     */
    void sendNewEventMessage(SseEventType sseEventType, String channelId, Long[] usersId);
}
