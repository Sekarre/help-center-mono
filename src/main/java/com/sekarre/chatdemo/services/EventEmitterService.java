package com.sekarre.chatdemo.services;

import com.sekarre.chatdemo.domain.enums.SseEventType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EventEmitterService {

    void removeEmitter();

    SseEmitter createNewEmitter();

    /**
     * Sends new event message to current logged-in user
     * @param sseEventType
     * @param payload
     */
    void sendNewEmitterMessage(SseEventType sseEventType, String payload);

    /**
     * Sends new event message to all users with id in usersId parameter
     * @param sseEventType
     * @param payload
     * @param usersId
     */
    void sendNewEmitterMessage(SseEventType sseEventType, String payload, Long[] usersId);
}
